package com.zephyr.common.dto;

import com.zephyr.common.constant.ClientType;
import com.zephyr.common.constant.LoginStatus;

public class UserContext {

	private Integer userId;
	private ClientType clientType;
	/*** token生成时间 */
	private Long genTime;
	private LoginStatus status;

	public UserContext() {
	}

	public UserContext(ClientType clientType) {
		this.userId = -1;
		this.clientType = clientType;
		this.status = LoginStatus.ONLINE;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public ClientType getClientType() {
		return clientType;
	}

	public void setClientType(ClientType clientType) {
		this.clientType = clientType;
	}

	public Long getGenTime() {
		return genTime;
	}

	public void setGenTime(Long genTime) {
		this.genTime = genTime;
	}

	public LoginStatus getStatus() {
		return status;
	}

	public void setStatus(LoginStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "UserContext [userId=" + userId + ", clientType=" + clientType + ", genTime=" + genTime + ", status="
				+ status + "]";
	}
}
