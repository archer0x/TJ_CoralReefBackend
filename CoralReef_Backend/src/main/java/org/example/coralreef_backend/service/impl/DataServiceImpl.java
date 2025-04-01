package org.example.coralreef_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.coralreef_backend.entity.CoralPhoto;
import org.example.coralreef_backend.entity.User;
import org.example.coralreef_backend.entity.YoloResponse;
import org.example.coralreef_backend.mapper.PhotoMapper;
import org.example.coralreef_backend.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.example.coralreef_backend.controller.LoginController.loginname;
import static org.example.coralreef_backend.controller.UploadPhotoController.resource;
import static org.example.coralreef_backend.service.impl.AIServiceImpl.yoloResponse;

@Service
public class DataServiceImpl implements DataService {
    @Autowired
    private PhotoMapper photoMapper;

    @Value("${result.dir}")
    private String resultDir; // 上传文件存储的目录

    @Override
    public ResponseEntity<String> saveData() {
        try {
            // 目标总存储文件夹
            String targetFolder = "D:/Code/backend/ResultPhoto";

//            String loginname="test"; //测试环境
            // 创建以 loginname 为文件夹名的目录
            Path userFolderPath = Paths.get(targetFolder, loginname);
            if (!Files.exists(userFolderPath)) {
                Files.createDirectories(userFolderPath); // 创建文件夹
            }
            String targetFolderUser = resultDir+loginname;

            // 获取当前时间并格式化为字符串
            LocalDateTime now = LocalDateTime.now();
            String formattedTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));

            // 处理 YOLO 返回的数据
            if (yoloResponse != null && yoloResponse.getRes() != null) {
                for (YoloResponse.Result result : yoloResponse.getRes().getResults()) {
                    String imagePath = result.getImage_path();
                    copyFileToFolder(imagePath, targetFolderUser);
                    CoralPhoto coralPhoto = new CoralPhoto();
                    coralPhoto.setName(result.getImage_name());
                    coralPhoto.setData(result.getSave_path());
                    coralPhoto.setStatus(result.getStatus());
                    coralPhoto.setConfidence(result.getDetections().get(0).getConfidence());
                    coralPhoto.setTime(formattedTime);
                    coralPhoto.setUsername(loginname);
                    photoMapper.save(coralPhoto);
                }

            } else {
               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("返回数据为空");
            }

            deleteFile(resultDir);
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
        return photoMapper.find(loginname);
    }

    @Override
    public List<CoralPhoto> getAllData(){
        return photoMapper.findAll();
    }

    @Override
    public int deleteData(String photoname){
        Assert.notNull(photoname, "photoname不能为空");
        return photoMapper.deleteData(photoname);
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

    @Override
    public void deleteFile(String filepath) throws IOException {
        Path userFolderPath = Paths.get(filepath, loginname);
        Files.list(userFolderPath)  // 列出文件夹中的所有文件
                .forEach(filePath -> {
                    try {
                        Files.delete(filePath); // 删除每个文件
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }
}
