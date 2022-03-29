package com.yyz.complie.application.impl;

import com.yyz.complie.application.UserService;
import com.yyz.complie.domain.CommonResult;
import com.yyz.complie.application.RedisAPI;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author yangyizhou
 * @create 2022/3/8 18:09
 */
@Component
public class UserServiceImpl implements UserService {
    @Override
    public CommonResult<Object> login(String username, String password) {
        if ("".equals(username) || "".equals(password)){
            return new CommonResult<>(400_41, "账号或者密码不能为空");
        }
        String serial = RedisAPI.get(username + "|" + password);
        if (serial == null) {
            return new CommonResult<>(400_40, "账号或者密码错误");
        } else {
            // 将用户信息存入redis
            RedisAPI.set(serial, username);
            return new CommonResult<>(200_00, serial);
        }

    }

    @Override
    public CommonResult<Object> register(String username, String password, String rePassword) {
        if (!password.equals(rePassword)) {
            return new CommonResult<>(400_41, "两次密码需一致");
        } else {
            // 查重
            String serial = RedisAPI.get(username + "|" + password);
            if(serial == null){
                serial = UUID.randomUUID().toString();
                RedisAPI.set(username + "|" + password, serial);
                // 将用户信息存入redis
                RedisAPI.set(serial, username);
                return new CommonResult<>(200_00, serial);
            } else {
                return new CommonResult<>(400_42, "用户已存在");
            }

        }
    }


    @Override
    public CommonResult<Object> seeHistory(String serial) {


        return null;
    }

}
