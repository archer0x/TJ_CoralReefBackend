package org.example.coralreef_backend.service;

import org.example.coralreef_backend.common.Result;
import org.example.coralreef_backend.entity.YoloResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface AIService {

    YoloResponse.Result YoloSingle() throws IOException;

    List<YoloResponse.Result> YoloMultiple() throws IOException;
}
