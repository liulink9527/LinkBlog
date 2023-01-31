package com.link.exception;

import com.link.enums.AppHttpCodeEnum;
import lombok.Data;

/**
 * @author Link
 * @Description
 * @date 2023-01-15 15:03
 */
public class SystemException extends RuntimeException{
    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }

}
