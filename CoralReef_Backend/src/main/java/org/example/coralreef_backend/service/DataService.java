package org.example.coralreef_backend.service;

import org.example.coralreef_backend.entity.CoralPhoto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DataService{

    ResponseEntity<String> saveData();

    List<CoralPhoto> getData();

    public void copyFileToFolder(String sourcePath, String targetDir);
}
