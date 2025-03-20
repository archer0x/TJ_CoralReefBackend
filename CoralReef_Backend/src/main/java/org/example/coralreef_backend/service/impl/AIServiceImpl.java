package org.example.coralreef_backend.service.impl;

import jakarta.servlet.http.HttpServletResponse;
import org.example.coralreef_backend.common.Result;
import org.example.coralreef_backend.entity.YoloResponse;
import org.example.coralreef_backend.service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.example.coralreef_backend.controller.UploadPhotoController.request;

@Service
public class AIServiceImpl implements AIService {
    public static YoloResponse yoloResponse;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String YoloSingle() {
        // YOLO 服务的地址
        String yoloUrl = "http://localhost:8081/predict";

        // 设置请求体内容，传入图片路径
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 需要传递的参数是 image_path
        String imagePath = request;
        String jsonBody = "{\"image_path\": \"" + imagePath + "\"}";

        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

        // 发送 POST 请求到 YOLO 服务
        ResponseEntity<YoloResponse> responseEntity = restTemplate.exchange(
                yoloUrl,
                HttpMethod.POST,
                requestEntity,
                YoloResponse.class // 解析为 YoloResponse 类型
        );

        // 获取 YOLO 返回的数据
        yoloResponse = responseEntity.getBody();

        // 处理 YOLO 返回的数据
        if (yoloResponse != null && yoloResponse.getRes() != null) {
            // 返回成功
            return yoloResponse.getRes().getPredict_folder();
        } else {
            // 发生错误，返回失败
            return "错误";
        }
    }
}