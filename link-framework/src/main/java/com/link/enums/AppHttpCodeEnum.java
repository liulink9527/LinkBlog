package com.link.enums;


/**
 * @author Link
 * @Description
 * @date 2022-12-25 17:54
 */
public enum AppHttpCodeEnum {

    SUCCESS(200, "操作成功"),
    NEED_LOGIN(401, "需要登录后操作"),
    NO_OPERATOR_AUTH(403, "无权限操作"),
    SYSTEM_ERROR(500, "系统错误"),
    USERNAME_EXIST(501, "用户已存在"),
    PHONE_EXIST(502, "手机号已存在"),
    EMAIL_EXIST(503, "邮箱已存在"),
    NICKNAME_EXIST(503, "名称已存在"),
    REQUIRE_USERNAME(504, "必须填写用户名"),
    LOGIN_ERROR(505, "用户名或密码错误"),
    USERNAME_NOT_NULL(508, "用户名不能为空"),
    EMAIL_NOT_NULL(508, "邮箱不能为空"),
    PASSWORD_NOT_NULL(508, "密码不能为空"),
    FILE_TYPE_ERROR(507, "文件类型上传错误");


    int code;
    String msg;

    AppHttpCodeEnum(int code, String message) {
        this.code = code;
        this.msg = message;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
