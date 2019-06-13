package com.zephyr.common.web;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author enpy cheung
 */
@ApiResponses({
        @ApiResponse(code=101, message="没有登录会话"),
        @ApiResponse(code=102, message="没有权限"),
        @ApiResponse(code=103, message="访问超时"),
        @ApiResponse(code=200, message="SUCCESS"),
        @ApiResponse(code=500, message="系统异常"),
})
public interface BaseApi {
}
