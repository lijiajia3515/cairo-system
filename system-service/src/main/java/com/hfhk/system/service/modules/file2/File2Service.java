package com.hfhk.system.service.modules.file2;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import io.minio.http.Method;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class File2Service {
	private final String bucket = "dev";
	private static final String TEMPORARY_PREFIX = "/temporary/";
	private final MinioClient client = MinioClient.builder().credentials("root", "Hfhk.1320.").endpoint("http://192.168.30.22:20000").build();

	public String getObjectUrl(String object) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
		return client.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().bucket(bucket).object(object).method(Method.GET).expiry(2, TimeUnit.HOURS).build());
	}

	public String getUploadObjectUrl(String object) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
		return client.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().bucket(bucket).object(object).method(Method.PUT).expiry(2, TimeUnit.HOURS).build());
	}

	public List<String> getUploadTemporaryObjectUrl(String filename) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
		String object = TEMPORARY_PREFIX.concat(filename);
		return Arrays.asList(object, client.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().bucket(bucket).object(object).method(Method.PUT).expiry(2, TimeUnit.HOURS).build()));
	}
}
