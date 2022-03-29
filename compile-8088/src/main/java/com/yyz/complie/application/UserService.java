package com.yyz.complie.application;

import com.yyz.complie.domain.CommonResult;
import org.springframework.stereotype.Component;

/**
 * @author yangyizhou
 * @create 2022/3/8 18:09
 */
@Component
public interface UserService {
    /**
     * 登录服务
     *
     * @param username
     * @param password
     * @return 20000成功，400_40账号或者密码错误
     */
    public CommonResult<Object> login(String username, String password);

    /**
     * 注册服务
     *
     * @param username
     * @param password
     * @param rePassword 两次密码需要一致
     * @return 400_41 两次密码不一致， 20000成功
     */
    public CommonResult<Object> register(String username, String password, String rePassword);


    /**
     * 查看用户所执行的文件
     *
     * @param serial
     * @return
     */
    public CommonResult<Object> seeHistory(String serial);
}