package com.zephyr.gateway.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.context.RequestContext;
import com.zephyr.common.constant.Constant;
import com.zephyr.common.dto.UserContext;
import com.zephyr.common.holder.HttpRequestHolder;
import com.zephyr.common.holder.UserContextHolder;
import com.zephyr.gateway.service.AccessTokenService;

@Component
public class PreAuthFilter extends AbstractFilter {

	private static Logger logger = LoggerFactory.getLogger(PreAuthFilter.class);
	
	@Autowired
	private AccessTokenService accessTokenService;
	
	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
//        HttpServletResponse response = ctx.getResponse();
        String url = request.getRequestURI();
        String userContextStr = HttpRequestHolder.getHeaderAttr(Constant.USER_CONTEXT);
        if (StringUtils.isBlank(userContextStr)) {
        	UserContext uc = accessTokenService.getUserContext();
        	if (uc != null) {
        		UserContextHolder.setUserContext(uc);
        	}
        	userContextStr = JSONObject.toJSONString(uc);
        }
        try {
        	request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("异常",e);
		}
        ctx.addZuulRequestHeader(Constant.USER_CONTEXT, userContextStr);
        
        StringBuilder result = new StringBuilder();
    	try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()))){
    		String t = null;
        	while ((t = reader.readLine()) != null) {
        		result.append(t);
        		if(t.contains("form-data")) {
        			result.setLength(0);
        			break;
        		}
        	}
    	} catch (IOException e) {
			logger.error("异常",e);
    	}
    	
    	String jsonParam = null;
    	if(result.length()>0) {
    		try {
    			JSONObject json = JSONObject.parseObject(result.toString());
    			jsonParam = json.toJSONString();
    		}catch(Exception e) {
    			jsonParam = result.toString();
    		}
    	}
    	
    	jsonParam =jsonParam +", " + JSONObject.toJSONString(request.getParameterMap());
        
        logger.info(String.format("%s request to %s, params:%s, UserInfo==>%s", request.getMethod(), url,jsonParam, userContextStr));
		return null;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public int filterOrder() {
		return 1;
	}
	
	/**
	 * filterType：返回一个字符串代表过滤器的类型，在zuul中定义了四种不同生命周期的过滤器类型，具体如下：
    	pre：可以在请求被路由之前调用
    	routing：在路由请求时候被调用
    	post：在routing和error过滤器之后被调用
    	error：处理请求时发生错误时被调用
    	filterOrder：通过int值来定义过滤器的执行顺序
    	shouldFilter：返回一个boolean类型来判断该过滤器是否要执行，所以通过此函数可实现过滤器的开关。在上例中，我们直接返回true，所以该过滤器总是生效。
    	run：过滤器的具体逻辑。需要注意，这里我们通过ctx.setSendZuulResponse(false)令zuul过滤该请求，不对其进行路由，
    		然后通过ctx.setResponseStatusCode(401)设置了其返回的错误码，当然我们也可以进一步优化我们的返回，比如，通过ctx.setResponseBody(body)对返回body内容进行编辑等。
	 */
	@Override
	public String filterType() {
		return "pre";
	}

}
