package com.example.blog.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${upload.path:/home/blog/uploads}")
    private String uploadPath;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/login")
                .excludePathPatterns("/admin")
                .excludePathPatterns("/admin/loginPage");

    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 添加自定义的静态资源映射
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadPath + "/");

        // 保留默认的静态资源映射
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

}
