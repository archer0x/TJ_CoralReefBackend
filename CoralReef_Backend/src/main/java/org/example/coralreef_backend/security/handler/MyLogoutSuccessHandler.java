package org.example.coralreef_backend.security.handler;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.coralreef_backend.common.Result;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;
import static org.example.coralreef_backend.controller.LoginController.loginname;
/**
 * 注销成功处理器
 */
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        loginname="";
        Result<Object> successResult = Result.success("注销成功");
        String jsonResult = JSON.toJSONString(successResult);

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(jsonResult);

    }
}
