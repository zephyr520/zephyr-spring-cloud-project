package com.zephyr.common.holder;

import com.zephyr.common.dto.UserContext;

public class UserContextHolder {

	private static final ThreadLocal<UserContext> USER_CONTEXT = new ThreadLocal<>();
	
	public static void setUserContext(UserContext userContext) {
		USER_CONTEXT.set(userContext);
	}
	
	public static UserContext getUserContext() {
		return USER_CONTEXT.get();
	}
	
	public static void clear() {
		USER_CONTEXT.remove();
	}
}
