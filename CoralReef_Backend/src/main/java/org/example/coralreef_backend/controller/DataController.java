package org.example.coralreef_backend.controller;

import org.example.coralreef_backend.entity.CoralPhoto;
import org.example.coralreef_backend.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.example.coralreef_backend.controller.UploadPhotoController.resource;


@RestController("/api/data")
public class DataController {
    @Autowired
    private DataService dataService;  // 注入 DataService

    // GET 请求，获取所有照片数据
    @GetMapping("/get_photo")
    public ResponseEntity<List<CoralPhoto>> getAllPhotos() {
        // 调用服务层获取数据
        List<CoralPhoto> photos = dataService.getData();

        // 返回查询到的照片数据，Spring Boot 会自动将 List<CoralPhoto> 转为 JSON 格式
        if (photos.isEmpty()) {
            return ResponseEntity.status(404).body(null);  // 如果没有数据，返回 404
        }

        System.out.println(ResponseEntity.ok(photos).getBody());
        return ResponseEntity.ok(photos);  // 返回 200 OK 和照片数据
    }

    @PostMapping("/save_photo")
    public ResponseEntity<String> savePhoto(@RequestBody CoralPhoto photo) {
        ResponseEntity<String> stringResponseEntity=dataService.saveData(photo);
        System.out.println(stringResponseEntity.getBody());
        return ResponseEntity.ok(stringResponseEntity.getBody());
    }
}
