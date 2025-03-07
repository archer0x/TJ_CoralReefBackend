package org.example.coralreef_backend.controller;

import org.example.coralreef_backend.entity.CoralPhoto;
import org.example.coralreef_backend.mapper.PhotoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.example.coralreef_backend.controller.UploadPhotoController.resource;

@RestController()
@RequestMapping("/api/test")
public class TestController {
    @Autowired
    private PhotoMapper photoMapper;

    @GetMapping("/process_image")
    public String test_py(@RequestBody String teststring) {
        System.out.println("testing: "+teststring);
        return teststring;
    }

    @PutMapping("/upload_photo")
    public ResponseEntity<String> testUpload() {
        try {
            // 获取当前时间并格式化为字符串
            LocalDateTime now = LocalDateTime.now();
            String formattedTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            // 创建 CoralPhoto 对象
            CoralPhoto coralPhoto = new CoralPhoto("test_photo", resource, "bleached", formattedTime);

            // 保存到数据库
            photoMapper.save(coralPhoto);

            // 返回成功信息
            return ResponseEntity.ok("上传成功！ID：" + coralPhoto.getId());
        } catch (Exception e) {
            e.printStackTrace();
            // 返回失败信息
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("上传失败：" + e.getMessage());
        }
    }
}