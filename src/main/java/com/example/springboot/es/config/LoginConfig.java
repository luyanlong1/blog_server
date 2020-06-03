package com.example.springboot.es.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName LoginConfig
 * @Description TODO
 * @Author 15258
 * @Date 2020/5/12 16:55
 * @Version 1.0
 */

@Configuration
public class LoginConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/aaaa/**")
                .excludePathPatterns("/login","/user/**","/css/**","/img/**","/js/**","/","/page/show/**","/page/login/**","/show/**");
    }
}
