package org.example.coralreef_backend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.coralreef_backend.entity.CoralPhoto;
import org.example.coralreef_backend.mapper.PhotoMapper;
import org.example.coralreef_backend.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.example.coralreef_backend.controller.UploadPhotoController.resource;

@Service
public class DataServiceImpl implements DataService {
    @Autowired
    private PhotoMapper photoMapper;


    @Override
    public ResponseEntity<String> saveData(CoralPhoto photo) {
        try {
            // 获取当前时间并格式化为字符串
            LocalDateTime now = LocalDateTime.now();
            String formattedTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            // 创建 CoralPhoto 对象
//            CoralPhoto coralPhoto = new CoralPhoto("test_photo", resource, "bleached", formattedTime);

            // 保存到数据库
            photoMapper.save(photo);

            // 返回成功信息
            return ResponseEntity.ok("上传成功！ID：" + photo.getId());
        } catch (Exception e) {
            e.printStackTrace();
            // 返回失败信息
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("上传失败：" + e.getMessage());
        }
    }

    @Override
    public List<CoralPhoto> getData() {
        return photoMapper.find();
    }
}
