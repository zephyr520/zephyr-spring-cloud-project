package com.zephyr.common.web;

/**
 * @author enpy cheung
 */
public enum ApiResultCode {

    /**
     * 返回结果代码
     */
	NO_LOGIN(101, "没有登录或会话超时"),
    NO_AUTH(102, "没有访问权限"),
    TIMEOUT_ACCESS(103, "访问超时"),
    TOKEN_INVALID(104, "非法Token"),
    TOKEN_EXPIRE(105, "Token过期"),
    ILLEGAL_ACCESS(106, "非法访问"),

    SUCCESS(200, "success"),
    NO_DATA(201, "未查找到数据"),
    PWD_ERROR(202, "用户名或密码错误"),
    MOBILE_ERROR(203, "手机号格式错误"),
    NO_ROLE(204, "用户未获得系统角色"),
    EXIST_DATA(205, "数据已存在"),
    
    PARAM_EMPTY(300, "请求参数为空"),
    
    DELETE_FORBID(401, "系统初始数据禁止删除"),
    
    SERVICE_ERROR(500, "sorry,系统开小差了"),
    
    OTHER_ERROR(999, "other error"),
    ;

    private final int code;
    private String message;

    ApiResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
