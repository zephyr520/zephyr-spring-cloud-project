package com.zephyr.common.exception;

import com.zephyr.common.web.ApiResultCode;

/**
 * @author enpy cheung
 */
public class AuthorizationException extends RuntimeException {

	private static final long serialVersionUID = -1350923594394880354L;

	private int code;
    private String msg;
	
	public AuthorizationException(String errMsg) {
		this.code = ApiResultCode.NO_AUTH.getCode();
		this.msg = errMsg;
	}
	
	public AuthorizationException(ApiResultCode resultCode) {
		this.code = resultCode.getCode();
		this.msg = resultCode.getMessage();
	}
	
	public AuthorizationException(int errCode, String errMsg) {
		this.code = errCode;
		this.msg = errMsg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
