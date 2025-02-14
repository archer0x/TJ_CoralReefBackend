package org.example.coralreef_backend.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {
    @CrossOrigin(origins = "*")
    @GetMapping("/process_image")
    public String test_py(@RequestBody String teststring) {
        System.out.println("testing: "+teststring);
        return teststring;
    }
}