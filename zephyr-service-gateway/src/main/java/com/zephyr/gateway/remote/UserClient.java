package com.zephyr.gateway.remote;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient("zephyr-service-user")
public interface UserClient {

	
}
