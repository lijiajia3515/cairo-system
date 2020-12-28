package com.hfhk.system.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "service-auth-v1")
public interface AClient {

	@GetMapping("/service/authentication")
	String authentication(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorization);
}
