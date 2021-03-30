package com.hfhk.system.service.config;

import com.hfhk.system.service.framework.minio.MinioProperties;
import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {
	@Bean
	public MinioClient minioClient(MinioProperties properties) {
		return MinioClient.builder().endpoint(properties.getEndpoint()).credentials(properties.getUsername(), properties.getPassword())
			.build();
	}
}
