package com.zephyr.gateway.filter;

import javax.servlet.http.HttpServletResponse;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public abstract class AbstractFilter extends ZuulFilter {
	
	/**
	 * 
	 * @param errorCode
	 * @param errorMsg
	 * @return
	 */
	protected void sendError(String errorCode, String errorMsg) {
		RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletResponse response = ctx.getResponse();
		StringBuilder content = new StringBuilder();
		content.append("{\"retCode\":\"");
		content.append(errorCode);
		content.append("\",\"retMsg\":\"");
		content.append(errorMsg);
		content.append("\",\"data\":\"");
		content.append("null");
		content.append("\"}");
		ctx.setSendZuulResponse(false);// 过滤该请求，不对其进行路由  
		ctx.setResponseStatusCode(200);// 返回错误码  
		response.setContentType("application/json;charset=UTF-8");
		ctx.setResponseBody(content.toString());
		ctx.set("isSuccess", false);  
	}
}
