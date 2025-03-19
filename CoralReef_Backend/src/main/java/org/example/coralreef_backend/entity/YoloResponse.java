package org.example.coralreef_backend.entity;

import java.util.List;

public class YoloResponse {
    private Res res;

    public Res getRes() {
        return res;
    }

    public void setRes(Res res) {
        this.res = res;
    }

    public static class Res {
        private String predict_folder;
        private List<Result> results;

        public String getPredict_folder() {
            return predict_folder;
        }

        public void setPredict_folder(String predict_folder) {
            this.predict_folder = predict_folder;
        }

        public List<Result> getResults() {
            return results;
        }

        public void setResults(List<Result> results) {
            this.results = results;
        }
    }

    public static class Result {
        private List<Detection> detections;
        private String image_path;

        public List<Detection> getDetections() {
            return detections;
        }

        public void setDetections(List<Detection> detections) {
            this.detections = detections;
        }

        public String getImage_path() {
            return image_path;
        }

        public void setImage_path(String image_path) {
            this.image_path = image_path;
        }
    }

    public static class Detection {
        private List<Integer> bounding_box;
        private int class_id;
        private String class_name;
        private double confidence;

        public List<Integer> getBounding_box() {
            return bounding_box;
        }

        public void setBounding_box(List<Integer> bounding_box) {
            this.bounding_box = bounding_box;
        }

        public int getClass_id() {
            return class_id;
        }

        public void setClass_id(int class_id) {
            this.class_id = class_id;
        }

        public String getClass_name() {
            return class_name;
        }

        public void setClass_name(String class_name) {
            this.class_name = class_name;
        }

        public double getConfidence() {
            return confidence;
        }

        public void setConfidence(double confidence) {
            this.confidence = confidence;
        }
    }
}
