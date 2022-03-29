package com.yyz.complie.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author yangyizhou
 * @create 2022/3/8 18:08
 */

@Component
@Data
public class User {
    String username;
    String password;
    String rePassword;
    String serial;
}
