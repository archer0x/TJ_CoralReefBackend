package org.example.coralreef_backend.security.handler;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.coralreef_backend.common.Result;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 认证成功后处理器
 */
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //获取用户身份信息
        Object principal = authentication.getPrincipal();
        //获取用户权限信息
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("principal", principal);
        userInfo.put("authorities", authorities);

        Result<Object> successResult = Result.success(userInfo);
        String jsonResult = JSON.toJSONString(successResult);

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(jsonResult);

    }
}
