package com.hfhk.system.service.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(
	basePackages = {"com.hfhk.**.client"}
)
@Configuration
public class FeignConfig {
}
