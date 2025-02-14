package org.example.coralreef_backend.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
public class UploadPhoto {
    public static String resource;

    public static String request;

    @Value("${upload.dir}")
    private String uploadDir; // 上传文件存储的目录

    @Value("${IP}")
    private String IP; // 映射ip路径

    @CrossOrigin(origins = "*")
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("上传的文件为空");
        }
        try {
            // 生成唯一的文件名
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            // 拼接上传文件的完整路径
            Path filePath = Paths.get(uploadDir, fileName);
            // 将文件保存到服务器
            Files.copy(file.getInputStream(), filePath);
            // 返回文件的访问路径
            String fileUrl = "/uploads/" + fileName;
            resource =IP+"UploadPhoto/"+fileName;
            request=uploadDir+fileName;
            System.out.println(request);
            System.out.println(resource);
            return ResponseEntity.ok().body(new UploadResponse(fileUrl));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("文件上传失败: " + e.getMessage());
        }
    }

    static class UploadResponse {
        private String imageUrl;

        public UploadResponse(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }
}
