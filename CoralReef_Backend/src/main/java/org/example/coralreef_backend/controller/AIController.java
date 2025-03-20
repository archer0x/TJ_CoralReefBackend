package org.example.coralreef_backend.controller;

import org.example.coralreef_backend.common.Result;
import org.example.coralreef_backend.service.AIService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@RestController
@RequestMapping("/api/yolo")
public class AIController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AIService aiService;

    @PostMapping("/single")
    public Result<Object> getYolo() {
        String result=aiService.YoloSingle();
        System.out.println(result);
        return Result.success(result);
    }

    @PostMapping("/multiple")
    public Result<Object> getYoloPhotos() {
        String result=aiService.YoloSingle();
        System.out.println(result);
        return Result.success(result);
    }
}
