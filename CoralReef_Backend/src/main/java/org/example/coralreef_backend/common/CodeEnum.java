package org.example.coralreef_backend.common;

import lombok.Getter;

/**
 * 状态码枚举
 */
@Getter
public enum CodeEnum {

    SUCCESS(200, "成功", "通用成功码"),
    FAIL(-1, "失败", "通用失败码"),
    AUTH_FAIL(401, "无权限", "无权限"),
    SYSTEM_ERROR(99999, "系统异常", "系统异常")
    ;

    private final int code;
    private final String msg;
    private final String desc;

    CodeEnum(Integer code, String msg, String desc) {
        this.code = code;
        this.msg = msg;
        this.desc = desc;
    }

}
