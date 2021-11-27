package com.example.restapi.config;

import com.example.restapi.web.interceptors.AdminInterceptor;
import com.example.restapi.web.interceptors.AuthenticateInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthenticateInterceptor())
                .addPathPatterns("/users/*");
        registry.addInterceptor(new AdminInterceptor())//
                .addPathPatterns("/users/all");
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Can just allow `methods` that you need.
        registry.addMapping("/**").allowedMethods("GET", "PUT", "POST", "DELETE", "OPTIONS", "PATCH");
    }
}
