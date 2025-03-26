package org.example.coralreef_backend.entity;

import lombok.*;
import lombok.experimental.Accessors;

@Data
@Getter
@Setter
public class Email {
    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户电话
     */
    private String phone;

    /**
     * 验证码
     */
    private String code;
}
