package com.yyz.complie.domain.compileModel;

import com.yyz.complie.domain.CommonResult;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author yangyizhou
 * @create 2022/3/8 12:31
 */
@Component
public class MyPythonCompiler extends MyCompilerTemplate implements MyCompilerI {
    private static final String TMP_DIR = "tmp/python-tmp";


    @Override
    public CommonResult<Object> compileOneFileAndExecute(String filename, String code, String serial) {
        this.type = "python3";
        String cache = tmpInit(TMP_DIR, filename, code, serial);

        if (cache != null) {
            return new CommonResult<>(200_00, cache);
        }


        String systemPath = System.getProperty("user.dir");

        List<String> command = Arrays.asList("python", systemPath + "\\" + savePath + "\\" + filename);

        ProcessBuilder builder = new ProcessBuilder(command);
        try {
            Process process = builder.start();
            StringBuilder err = getProcessErr(process);
            StringBuilder res = getProcessInfo(process);
            String returnRes = res.append(err).toString();
            saveCache(returnRes);
            return new CommonResult<>(200_00, returnRes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new CommonResult<>(400_00, "服务器内部错误，请和管理员联系");
    }

}
