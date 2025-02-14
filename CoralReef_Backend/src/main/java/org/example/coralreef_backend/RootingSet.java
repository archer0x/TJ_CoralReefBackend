package org.example.coralreef_backend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RootingSet implements WebMvcConfigurer {

    @Value("${upload.dir}")
    private String uploadDir; // 上传文件存储的目录

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 访问地址：如http://localhost:8080/photo/1.jpg
        registry.addResourceHandler("/UploadPhoto/**")
                // 访问http://localhost:8080/photo/1.jpg 会自动找C:\Users\Lenovo\Desktop\resource\ 路径下名为1.jpg图片
                .addResourceLocations("file:" + uploadDir);
    }
}