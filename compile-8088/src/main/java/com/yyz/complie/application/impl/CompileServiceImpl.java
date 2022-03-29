package com.yyz.complie.application.impl;

import com.yyz.complie.application.CompileService;
import com.yyz.complie.domain.CommonResult;
import com.yyz.complie.domain.compileModel.MyCompilerFactory;
import com.yyz.complie.domain.compileModel.MyCompilerI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author yangyizhou
 * @create 2022/3/7 14:06
 */
@Component
@Slf4j
public class CompileServiceImpl implements CompileService {

    final int NOT_FOUND_TYPE_INT = 400_04;
    final int SUCCESS_INT = 200_00;
    final int ERROR_INT = 500_00;
    final String NOT_FOUND_TYPE_STRING = "编译器类型未找到";
    final String ERROR_STRING = "服务器内部错误，请联系服务器管理员";

    @Override
    public CommonResult<Object> compileOneFile(String filename, String code, String type, String serial) {


        MyCompilerI compiler = MyCompilerFactory.newInstance(type);

        if (compiler == null) {
            return new CommonResult<>(NOT_FOUND_TYPE_INT, NOT_FOUND_TYPE_STRING);
        }
        return compiler.compileOneFileAndExecute(filename, code, serial);


    }
}
