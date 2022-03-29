package com.yyz.complie.resource;

import com.sun.org.apache.regexp.internal.RE;
import com.yyz.complie.application.CompileService;
import com.yyz.complie.domain.CodeFile;
import com.yyz.complie.domain.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author yangyizhou
 * @create 2022/3/7 13:27
 */
@Api(value = "编译资源", tags = {"编译操作接口"})
@RestController
@RequestMapping("/onlineIde/api/v1/compile")
@AllArgsConstructor
public class CompileResource {
    CompileService compileService;

    @GetMapping("/test")
    @ApiOperation(value = "测试用例", httpMethod = "GET")
    public String test(Integer id) {
        return "test ： " + Thread.currentThread().getName() + ",test, id " + id;
    }

    @PostMapping("/")
    @ApiOperation(value = "编译", httpMethod = "POST")
    public CommonResult<Object> compile(@RequestBody CodeFile codeFile) {

        return compileService.compileOneFile(codeFile.getFilename(), codeFile.getCode(), codeFile.getType(), codeFile.getSerial());
    }

}
