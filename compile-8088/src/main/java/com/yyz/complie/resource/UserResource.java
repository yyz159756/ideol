package com.yyz.complie.resource;

import com.yyz.complie.application.UserService;
import com.yyz.complie.domain.CodeFile;
import com.yyz.complie.domain.CommonResult;
import com.yyz.complie.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangyizhou
 * @create 2022/3/8 18:35
 */
@Api(value = "用户资源", tags = {"用户操作接口"})
@RestController
@RequestMapping("/onlineIde/api/v1/user")
@AllArgsConstructor
public class UserResource {
    private UserService userService;

    @PostMapping("/login")
    @ApiOperation(value = "登录", httpMethod = "POST")
    public CommonResult<Object> login(@RequestBody User user) {
        return userService.login(user.getUsername(), user.getPassword());
    }

    @PostMapping("/register")
    @ApiOperation(value = "注册", httpMethod = "POST")
    public CommonResult<Object> register(@RequestBody User user) {
        return userService.register(user.getUsername(), user.getPassword(), user.getRePassword());
    }

    @PostMapping("/seeHistory")
    @ApiOperation(value = "注册", httpMethod = "POST")
    public CommonResult<Object> seeHistory(@RequestBody String serial) {
        return userService.seeHistory(serial);
    }
}
