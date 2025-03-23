package org.example.coralreef_backend.controller;

import org.example.coralreef_backend.common.Result;
import org.example.coralreef_backend.entity.YoloResponse;
import org.example.coralreef_backend.service.AIService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/yolo")
public class AIController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AIService aiService;

    @PostMapping("/single")
    public ResponseEntity<YoloResponse.Result> getYolo() throws IOException {
        YoloResponse.Result result=aiService.YoloSingle();
        System.out.println(result);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/multiple")
    public ResponseEntity<List<YoloResponse.Result>> getYoloPhotos() throws IOException {
        List<YoloResponse.Result> results=aiService.YoloMultiple();
        return ResponseEntity.ok(results);
    }
}
