package com.yyz.complie.domain.compileModel;

import com.yyz.complie.application.RedisAPI;
import org.springframework.stereotype.Component;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.util.UUID;

/**
 * @author yangyizhou
 * @create 2022/3/7 19:24
 */
@Component
public class MyCompilerTemplate {

    static final int COMPILE_FAILED_INT = 400_05;
    static final String NULL = "null";

    /***
     * 原生的文件名字
     */
    String pureFilename;
    /***
     * 文件绝对路径，filename:TMP_DIR/serial/filename.*
     */
    String filenamePath;
    String className;
    String serial;
    String savePath;
    /***
     * 文件中的代码
     */
    String code;
    JavaCompiler compiler;
    String outputPath;
    String errorOutputPath;
    String type;


    public String tmpInit(String tmpDir, String filename, String code, String serial) {
        this.pureFilename = filename;
        if (null == serial) {
            // 生成随机序列serial，作为文件夹名
            this.serial = UUID.randomUUID().toString();
        } else {
            this.serial = serial;
        }
        // 存放文件地址，放在tmp目录下：TMP_DIR/serial+filename
        this.savePath = String.format("%s\\%s", tmpDir, this.serial);
        this.className = getDeSuffixFileName(filename);
        this.filenamePath = String.format("%s\\%s", savePath, filename);

        this.outputPath = String.format("%s\\%s-output.txt", savePath, className);
        this.errorOutputPath = String.format("%s\\%s-error-output.txt", savePath, className);
        this.code = code;
        this.compiler = ToolProvider.getSystemJavaCompiler();
        // todo 重构出去findCache对外暴露接口
        String cache = findCache();
        if (cache != null) {
            return cache;
        } else {
            createTmpDirectory(tmpDir);
            createTmpJavaFile();
            return null;
        }

    }

    /**
     * 创建文件夹 tmp/*-tmp
     */
    public void createTmpDirectory(String tmpDir) {
        File folder = new File(String.format("%s/%s", tmpDir, serial));
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }
        if (!success) {
            throw new RuntimeException("创建失败");
        }
    }

    /***
     * 创建.java/.py/.cpp文件放在tmpDir目录下，生成filename
     */
    public void createTmpJavaFile() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filenamePath));
            bw.write(code);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public StringBuilder getProcessErr(Process process) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        String str;
        StringBuilder errResult = new StringBuilder("");
        while ((str = bufferedReader.readLine()) != null) {
            errResult.append(str).append("\n");
        }
        return errResult;
    }


    public StringBuilder getProcessInfo(Process process) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String str;
        StringBuilder execResult = new StringBuilder("");
        while ((str = bufferedReader.readLine()) != null) {
            execResult.append(str).append("\n");
        }
        return execResult;
    }

    /***
     * 去文件后缀
     * @param filename 文件名
     * @return String 去文件后缀的文件名
     */
    String getDeSuffixFileName(String filename) {
        return filename.replaceAll("\\..*$", "");
    }
    String getCacheKey(){
        return pureFilename + "|" + type +"|" + code;
    }
    void saveCache(String result) {
        RedisAPI.set(getCacheKey(), result);
    }

    String findCache() {
        return RedisAPI.get(getCacheKey());
    }

}
