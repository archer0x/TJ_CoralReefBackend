package org.example.coralreef_backend.common.exception;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import org.example.coralreef_backend.common.CodeEnum;

/**
 * 自定义异常
 */
@Getter
public class CustomException extends RuntimeException{

    private final Integer code;
    private final String msg;
    private final String desc;

    public CustomException(Integer code, String msg, String desc){
        super(msg);
        this.code = code;
        this.msg = msg;
        this.desc = desc;
    }

    public CustomException(CodeEnum codeEnum){
        this(codeEnum.getCode(), codeEnum.getMsg(), codeEnum.getDesc());
    }

    public CustomException(CodeEnum codeEnum, Object template1, Object template2){
        this(codeEnum.getCode(), StrUtil.format(codeEnum.getMsg(), template1), StrUtil.format(codeEnum.getDesc(), template2));
    }

}
