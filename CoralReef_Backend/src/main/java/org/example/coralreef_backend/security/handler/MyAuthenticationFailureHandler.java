package org.example.coralreef_backend.security.handler;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.coralreef_backend.common.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.util.HashMap;

/**
 * 认证失败处理器
 */
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        //获取异常信息
        String localizedMessage = exception.getLocalizedMessage();
        HashMap result = new HashMap();
        result.put("data",localizedMessage);
        result.put("extra","认证失败");
        //将异常信息放入自定义result
        Result<Object> failureResult = Result.failure(result);
        //返回结果转为json字符串
        String jsonResult = JSON.toJSONString(failureResult);

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(jsonResult);

    }
}
