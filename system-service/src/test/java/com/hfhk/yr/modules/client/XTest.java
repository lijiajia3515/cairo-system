package com.hfhk.yr.modules.client;

import io.minio.MinioClient;
import io.minio.PostPolicy;
import io.minio.errors.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.util.Map;

@SpringBootTest
public class XTest {
	@Test
	public static void main(String[] args) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
		MinioClient client = MinioClient.builder().credentials("root", "Hfhk.1320.").build();
		PostPolicy dev = new PostPolicy("dev", ZonedDateTime.now().plusDays(2));
		Map<String, String> presignedPostFormData = client.getPresignedPostFormData(dev);
		presignedPostFormData.forEach((k, v) -> {
			System.out.printf(String.format("[%s , %s]\n", k, v));
		});
	}
}
