package org.example.coralreef_backend.service;

import org.example.coralreef_backend.entity.CoralPhoto;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface DataService{

    ResponseEntity<String> saveData();

    List<CoralPhoto> getData();

    List<CoralPhoto> getAllData();

    int deleteData(String photoname);

    void copyFileToFolder(String sourcePath, String targetDir);

    void deleteFile(String filepath) throws IOException;
}
