package com.zephyr.common.web;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author enpy cheung
 */
public class ApiResult<T> implements Serializable {


    private static final long serialVersionUID = -1730621878516713508L;
    @ApiModelProperty(value = "状态码")
    private int retCode;
    @ApiModelProperty(value = "状态码描述")
    private String retMsg;
    @ApiModelProperty(value = "返回数据")
    private T data;

    /**
     * 只返回成功。
     */
    public ApiResult() {
        this.retCode = ApiResultCode.SUCCESS.getCode();
        this.retMsg = ApiResultCode.SUCCESS.getMessage();
        this.data = null;
    }

    /**
     * 返回成功，并且携带数据。
     * @param data
     */
    public ApiResult(T data) {
        this.retCode = ApiResultCode.SUCCESS.getCode();
        this.retMsg = ApiResultCode.SUCCESS.getMessage();
        this.data = data;

    }

    public ApiResult(ApiResultCode resultCode) {
        this.retCode = resultCode.getCode();
        this.retMsg = resultCode.getMessage();
        this.data = null;
    }

    /**
     * 返回自定义错误码，不携带数据。
     * @param retCode
     * @param retMsg
     */
    public ApiResult(int retCode, String retMsg) {
        this.retCode = retCode;
        this.retMsg = retMsg;
        this.data = null;
    }

    /**
     * 返回自定义错误码，并且携带数据。
     */
    public ApiResult(int retCode, String retMsg, T data) {
        this.retCode = retCode;
        this.retMsg = retMsg;
        this.data = data;
    }

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ApiResult{" +
                "retCode='" + retCode + '\'' +
                ", retMsg='" + retMsg + '\'' +
                ", data=" + data +
                '}';
    }
}
