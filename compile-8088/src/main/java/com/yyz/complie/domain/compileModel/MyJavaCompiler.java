package com.yyz.complie.domain.compileModel;

import com.yyz.complie.domain.CommonResult;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * @author yangyizhou
 * @create 2022/3/7 16:39
 */
@NoArgsConstructor
@Component
public class MyJavaCompiler extends MyCompilerTemplate implements MyCompilerI {
    private static final String TMP_DIR = "tmp/java-tmp";


    @SneakyThrows
    @Override
    public CommonResult<Object> compileOneFileAndExecute(String filename, String code,String serial) {
        this.type = "java";
        String cache = tmpInit(TMP_DIR, filename, code, serial);

        if (cache != null) {
            return new CommonResult<>(200_00, cache);
        }

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();


        int result = compiler.run(System.in, outStream, errStream, this.filenamePath);
        System.out.printf("result: %d%n", result);
        System.out.println(result == 0 ? "编译成功" : "编译失败");

        if (result == 1) {
            System.out.println(errStream);
            saveCache(errStream.toString());
            return new CommonResult<>(COMPILE_FAILED_INT, errStream.toString());
        }

        Process process = Runtime.getRuntime().exec(String.format("java %s", className), null, new File(savePath));

        StringBuilder execResult = getProcessInfo(process);

        outStream.close();
        errStream.close();
        saveCache(execResult.toString());
        return new CommonResult<>(200_00, execResult.toString());

    }


}
