package org.example.coralreef_backend.controller;

import lombok.Getter;
import lombok.Setter;
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

import static org.example.coralreef_backend.controller.LoginController.loginname;

@RestController
public class UploadPhotoController {
    public static String resource;

    public static String request;


    @Value("${upload.dir}")
    private String uploadDir; // 上传文件存储的目录

    @Value("${IP}")
    private String IP; // 映射ip路径

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("上传的文件为空");
        }

        try {
            String loginname="test"; //测试环境
            // 创建以 loginname 为文件夹名的目录
            Path userFolderPath = Paths.get(uploadDir, loginname);
            if (!Files.exists(userFolderPath)) {
                Files.createDirectories(userFolderPath); // 创建文件夹
            }

            // 生成唯一的文件名
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            // 拼接文件的存储路径，文件存储到 loginname 目录下
            Path filePath = userFolderPath.resolve(fileName);

            // 将文件保存到指定路径
            Files.copy(file.getInputStream(), filePath);

            // 返回文件的访问路径
            String fileUrl = "/uploads/" + loginname + "/" + fileName;
            resource = IP + "UploadPhoto/" + loginname + "/" + fileName;
            request = userFolderPath + "/" + fileName; // Windows路径格式
            request=request.replace("\\", "/");

            // 打印路径信息
            System.out.println(request);
            System.out.println(resource);

            // 返回文件的 URL
            return ResponseEntity.ok().body(new UploadResponse(fileUrl));

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("文件上传失败: " + e.getMessage());
        }
    }

    @Getter
    @Setter
    static class UploadResponse {
        private String imageUrl;
        public UploadResponse(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }
}
