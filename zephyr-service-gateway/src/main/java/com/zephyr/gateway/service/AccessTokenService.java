package com.zephyr.gateway.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.zephyr.common.constant.Constant;
import com.zephyr.common.dto.UserContext;
import com.zephyr.common.holder.HttpRequestHolder;
import com.zephyr.common.service.RedisService;
import com.zephyr.gateway.util.TokenUtil;

@Service
public class AccessTokenService {

	private final static Logger logger = LoggerFactory.getLogger(AccessTokenService.class);

	private final static Pattern PTN = Pattern.compile("^([\\w]+)[\\s]{1}([\\w]+)$");
	private final static String AUTHORIZATION_TYPE_BEARER = "Bearer";
//	private final static String AUTHORIZATION_TYPE_REFRESH = "Refresh";
	
	@Autowired
	protected RedisService redisService;
	
	public UserContext getUserContext() {
		UserContext uc = null;
		String userContextStr = HttpRequestHolder.getHeaderAttr(Constant.USER_CONTEXT);
		if (StringUtils.isNotBlank(userContextStr)) {
			uc = JSONObject.parseObject(userContextStr, UserContext.class);
		} else {
			String accessToken = HttpRequestHolder.getHeaderAttr(Constant.XAUTH_TOKEN_HEADER_NAME);
			if (StringUtils.isBlank(accessToken)) {
				accessToken = HttpRequestHolder.getParam(Constant.XAUTH_TOKEN_PARAMETER_NAME);
				if (StringUtils.isBlank(accessToken)) {
					return null;
				} else {
					try {
						accessToken = URLDecoder.decode(accessToken, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
			
			Matcher matcher = PTN.matcher(accessToken);
			if(matcher.matches()) {
				String tokenType = matcher.group(1);
				String token = matcher.group(2);
				if(AUTHORIZATION_TYPE_BEARER.equalsIgnoreCase(tokenType)) {
					uc = verifyAccessToken(token);
				}
			}else {
				logger.error("AccessToken parse failed, accessToken={}", accessToken);
			}
		}
			
		return uc;
	}

	private UserContext verifyAccessToken(String token) {
		UserContext userContext = TokenUtil.getUserIdAndClientTypeFromAccessToken(token);
		return userContext;
	}
}
