package org.example.coralreef_backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration  // 标记为配置类
public class WebConfig implements WebMvcConfigurer {
    @Value("${upload.dir}")
    private String uploadDir; // 上传文件存储的目录

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 访问地址：如http://localhost:8080/photo/1.jpg
        registry.addResourceHandler("/UploadPhoto/**")
                // 访问http://localhost:8080/photo/1.jpg 会自动找C:\Users\Lenovo\Desktop\resource\ 路径下名为1.jpg图片
                .addResourceLocations("file:" + uploadDir);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 允许跨域请求的路径
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173","https://*")  // 允许所有 HTTPS 前端地址
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // 允许的请求方式
                .allowedHeaders("*")  // 允许的请求头
                .allowCredentials(true);  // 是否允许携带凭证（如 Cookie）
    }
}
