package com.zephyr.common.exception.handler;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zephyr.common.exception.BOException;
import com.zephyr.common.web.ApiResult;
import com.zephyr.common.web.ApiResultCode;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleExceptions(HttpServletRequest request, Exception ex) {
    	ex.printStackTrace();
        String uri = request.getRequestURI();
        logger.error("Error occurred request URI : {}", uri);
        logger.error("系统异常信息：{}", ex.getMessage());
		logger.error(ex.getMessage(), ex);
		return new ApiResult<>(ApiResultCode.OTHER_ERROR.getCode(), "sorry,系统开小差了...");
    }

    @ExceptionHandler (BOException.class)
    @ResponseBody
    public Object handleBusinessExceptions(HttpServletRequest request,BOException ex) {
    	logger.error("errCode: {}, errMsg: {}, data: {}", ex.getCode(), ex.getMsg());
        if(ex.getMsg()!=null) {
            return new ApiResult<>(ex.getCode(),ex.getMsg());
        }
        return new ApiResult<>(ex.getCode(), ex.getMsg());
    }
}
