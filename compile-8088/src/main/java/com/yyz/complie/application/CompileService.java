package com.yyz.complie.application;

import com.yyz.complie.domain.CommonResult;
import org.springframework.stereotype.Component;

/**
 * @author yangyizhou
 * @create 2022/3/7 14:06
 */
@Component
public interface CompileService {


    /**
     * 编译一个文件，并返回编译结果
     *
     * @param filename 文件名称
     * @param code     源代码
     * @param type     编译器类型
     * @return 编译信息
     */
    CommonResult<Object> compileOneFile(String filename, String code, String type, String serial);
}
