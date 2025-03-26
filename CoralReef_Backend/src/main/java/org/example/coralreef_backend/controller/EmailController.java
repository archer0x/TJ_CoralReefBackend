package org.example.coralreef_backend.controller;

import jakarta.servlet.http.HttpSession;
import org.example.coralreef_backend.common.Result;
import org.example.coralreef_backend.security.servcie.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/sendEmail")
    public Result<Object> sendEmail(@RequestBody String email, HttpSession httpSession){
        emailService.sendMimeMail(email, httpSession);
        return Result.success("已发送验证码");
    }
}
