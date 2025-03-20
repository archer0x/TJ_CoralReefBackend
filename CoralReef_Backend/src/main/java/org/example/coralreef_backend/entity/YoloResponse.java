package org.example.coralreef_backend.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class YoloResponse {
    private Res res;

    @Setter
    @Getter
    public static class Res {
        private String predict_folder;
        private List<Result> results;
    }

    @Setter
    @Getter
    public static class Result {
        private List<Detection> detections;
        private String image_path;
        private String image_name; // 新增字段
        private String save_path;  // 新增字段
        private String status;     // 新增字段
    }

    @Setter
    @Getter
    public static class Detection {
        private List<Integer> bounding_box;
        private int class_id;
        private double confidence;
    }
}
