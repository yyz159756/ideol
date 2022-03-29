package com.yyz.complie.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author yangyizhou
 * @create 2022/3/7 14:08
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
@Builder
public class CommonResult<T> {
    private Integer code;
    private String message;
    private T data;

    public CommonResult(Integer code, String message) {
        this(code, message, null);
    }
}