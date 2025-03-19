package org.example.coralreef_backend.controller;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.http.HttpServletResponse;
import org.example.coralreef_backend.common.JwtUtil;
import org.example.coralreef_backend.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("")
@ResponseStatus(HttpStatus.OK) // 成功时默认200，不需要显式设置
public class LoginController {
    public static String loginname;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/myLogin")
    public Result<Object> myLogin(@RequestParam String username,
                                  @RequestParam String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

            String token = jwtUtil.generateToken(username);

            System.out.println("登录成功："+token);
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("username", username);
//            userInfo.put("password", password);
            userInfo.put("jwt", token);

            loginname=username;

            return Result.success(userInfo);

        } catch (AuthenticationException ex) {
            return Result.failure(HttpServletResponse.SC_UNAUTHORIZED, "登录失败");
        }
    }
}
