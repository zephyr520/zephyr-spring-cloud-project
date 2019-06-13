package com.zephyr.common.constant;

public enum ClientType {
	// 移动端
	ANDROID(1), IOS(2),
	WEB(3), WECHAT(4),
	// 后台系统
	ADMIN(9)
	;
	
	private Integer intVal;
	
	private ClientType(Integer intVal) {
		this.intVal = intVal;
	}
	
	public Integer getValue() {
		return this.intVal;
	}
	
	public static ClientType getClientType(String type) {
		for(ClientType ct : values()) {
			if(ct.name().equalsIgnoreCase(type)) {
				return ct;
			}
		}
		return null;
	}
}
