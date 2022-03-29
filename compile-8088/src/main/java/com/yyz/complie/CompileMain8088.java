package com.yyz.complie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @author yangyizhou
 * @create 2022/3/7 13:34
 */
@SpringBootApplication
@EnableOpenApi
public class CompileMain8088 {
    public static void main(String[] args) {
        SpringApplication.run(CompileMain8088.class, args);
    }
}
