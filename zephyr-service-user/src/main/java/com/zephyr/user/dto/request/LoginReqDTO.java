package com.zephyr.user.dto.request;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("用户登录请求参数")
public class LoginReqDTO implements Serializable{

    private static final long serialVersionUID = 4454478405713791379L;
    @ApiModelProperty("用户名")
    @NotBlank(message = "请输入用户名")
    private String username;
    @ApiModelProperty("密码")
    @NotBlank(message = "请输入密码")
    private String password;
    @ApiModelProperty(value="客户端类型", required=true, example="ANDROID,IOS,ADMIN") 
    private String clientType;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}
}
