package com.yyz.complie.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author yangyizhou
 * @create 2022/3/9 9:03
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**").
                allowedMethods("*").
                allowedOrigins("*").
                allowedHeaders("*");
    }

}
