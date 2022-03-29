package com.yyz.complie.domain.compileModel;

import com.yyz.complie.domain.CommonResult;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author yangizhou
 * @create 2022/3/7 14:21
 */
@Component
public interface MyCompilerI {

    /***
     * 编译一个文件 返回编译执行结果
     * @param filename 文件名
     * @param code 源带码
     * @throws IOException
     * @return String 返回编译执行结果
     */
    public CommonResult<Object> compileOneFileAndExecute(String filename, String code, String serial);
}
