package org.example.coralreef_backend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.coralreef_backend.entity.CoralPhoto;
import org.example.coralreef_backend.entity.YoloResponse;
import org.example.coralreef_backend.mapper.PhotoMapper;
import org.example.coralreef_backend.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.example.coralreef_backend.controller.UploadPhotoController.resource;
import static org.example.coralreef_backend.service.impl.AIServiceImpl.yoloResponse;

@Service
public class DataServiceImpl implements DataService {
    @Autowired
    private PhotoMapper photoMapper;

    @Override
    public ResponseEntity<String> saveData() {
        try {
            // 目标总存储文件夹
            String targetFolder = "D:/Code/backend/ResultPhoto";

            String loginname="test"; //测试环境
            // 创建以 loginname 为文件夹名的目录
            Path userFolderPath = Paths.get(targetFolder, loginname);
            if (!Files.exists(userFolderPath)) {
                Files.createDirectories(userFolderPath); // 创建文件夹
            }
            String targetFolderUser = "D:/Code/backend/ResultPhoto"+"/"+loginname;

            // 获取当前时间并格式化为字符串
            LocalDateTime now = LocalDateTime.now();
            String formattedTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            // 处理 YOLO 返回的数据
            if (yoloResponse != null && yoloResponse.getRes() != null) {
                for (YoloResponse.Result result : yoloResponse.getRes().getResults()) {
                    String imagePath = result.getImage_path();
                    copyFileToFolder(imagePath, targetFolderUser);
                    CoralPhoto coralPhoto = new CoralPhoto();
                    coralPhoto.setName(result.getImage_name());
                    coralPhoto.setData(result.getSave_path());
                    coralPhoto.setStatus(result.getStatus());
                    coralPhoto.setTime(formattedTime);
                    photoMapper.save(coralPhoto);
                }

            } else {
               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("返回数据为空");
            }

            // 创建 CoralPhoto 对象
//            CoralPhoto coralPhoto = new CoralPhoto("test_photo", resource, "bleached", formattedTime);

            // 保存到数据库
//            photoMapper.save(photo);

            // 返回成功信息
            return ResponseEntity.ok("上传成功！");
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


    /**
     * 复制文件到目标文件夹
     */
    @Override
    public void copyFileToFolder(String sourcePath, String targetDir) {
        File sourceFile = new File(sourcePath);
        File targetFile = new File(targetDir, sourceFile.getName());

        try {
            Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("文件已复制: " + sourceFile.getPath() + " -> " + targetFile.getPath());
        } catch (IOException e) {
            System.err.println("文件复制失败: " + sourceFile.getPath());
            e.printStackTrace();
        }
    }
}
