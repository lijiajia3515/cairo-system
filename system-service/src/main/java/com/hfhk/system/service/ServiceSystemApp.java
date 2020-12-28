package com.hfhk.system.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.consul.ConditionalOnConsulEnabled;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(
	basePackages = {"com.hfhk.**.client"}
)
public class ServiceSystemApp {

	public static void main(String[] args) {
		SpringApplication.run(ServiceSystemApp.class, args);
	}

}
