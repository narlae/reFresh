package com.onlyfresh.devkurly.config;

import com.onlyfresh.devkurly.web.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/login","/register/**","/", "/home" , "/error", "/icon/**", "/css/**", "/admin/**",
                        "/detail/**","/board/**" , "/product/**", "/products/**" , "/*.svg", "/js/**", "/imgs/**",
                        "/test/**", "/cart/**");
    }
}
