package com.yyz.complie.domain.compileModel;

import com.yyz.complie.domain.CommonResult;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * @author yangyizhou
 * @create 2022/3/7 19:23
 */
@Slf4j
@Component
public class MyGccCompiler extends MyCompilerTemplate implements MyCompilerI {
    private static final String TMP_DIR = "tmp/gcc-tmp";


    @SneakyThrows
    @Override
    public CommonResult<Object> compileOneFileAndExecute(String filename, String code, String serial) {
        this.type = "gcc";
        String cache = tmpInit(TMP_DIR, filename, code, serial);

        if (cache != null) {
            return new CommonResult<>(200_00, cache);
        }

        String systemPath = System.getProperty("user.dir");
        // cpp的绝对路径
        String cppFilepath = systemPath + "\\" + savePath + "\\" + filename;
        String cmd = ("cmd.exe /c g++ " + cppFilepath);
        System.out.println(cmd);
        Process process = Runtime.getRuntime().exec(cmd, null, new File(systemPath + "\\" + savePath));
        process.waitFor();

        StringBuilder errRes = getProcessErr(process);

        if (!"".equals(errRes.toString())){
            return new CommonResult<>(400_05, errRes.toString());
        }

        System.out.println(errRes);
        log.info(errRes.toString());


        StringBuilder exeRes = executeExe();
        log.info(exeRes.toString());

        String returnRes = errRes.append(exeRes).toString();
        // 保存到redis
        saveCache(returnRes);
        return new CommonResult<>(200_00, returnRes);
    }


    @SneakyThrows
    public StringBuilder executeExe() {
        String systemPath = System.getProperty("user.dir");
        String exeFilepath = systemPath + "\\" + savePath + "\\a.exe";
        String cmd = ("cmd.exe /c " + exeFilepath);
        Process process = Runtime.getRuntime().exec(cmd);
        return getProcessInfo(process);

    }

}
