package com.zephyr.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.zephyr.common.annotation.ApiAuthorized;
import com.zephyr.common.constant.Constant;
import com.zephyr.common.dto.UserContext;
import com.zephyr.common.exception.AuthorizationException;
import com.zephyr.common.holder.HttpRequestHolder;
import com.zephyr.common.holder.UserContextHolder;
import com.zephyr.common.service.CommonService;
import com.zephyr.common.web.ApiResultCode;

/**
 * @author enpy cheung
 * @DATE 2018/8/24
 * @description 后台系统操作权限码的注解验权
 */
@Aspect
@Component
public class AuthorizedAspect {

    @Autowired
    CommonService commonService;
    
    private UserContext getUserContext() {
		String userContextStr = HttpRequestHolder.getHeaderAttr(Constant.USER_CONTEXT);
		return JSONObject.parseObject(userContextStr, UserContext.class);
	}

    @Around(value = "@annotation(com.zephyr.common.annotation.ApiAuthorized)")
    public Object executeAuthorized(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        UserContext userContext =  getUserContext();
        if (userContext == null) {
            throw new AuthorizationException(ApiResultCode.NO_LOGIN);
        }
        UserContextHolder.setUserContext(userContext);
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        ApiAuthorized apiAuthorized = signature.getMethod().getAnnotation(ApiAuthorized.class);
        if (apiAuthorized != null) {
            String permitIdentifier = apiAuthorized.value();
            Boolean isHasPermission = commonService.verifyUserOperAuthorized(userContext.getUserId(), permitIdentifier);
            if (!isHasPermission) {
                throw new AuthorizationException(ApiResultCode.NO_AUTH);
            }
        }
        result = joinPoint.proceed();
        return result;
    }
}
