package com.hfhk.system.service.modules.file2;

import cn.hutool.core.util.IdUtil;
import com.hfhk.system.service.framework.minio.MinioProperties;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
public class File2Service {
	private static final String TEMPORARY_PREFIX = "temporary/";
	private final MinioClient client;
	private final String bucket;

	public File2Service(MinioClient client, MinioProperties properties) {
		this.client = client;
		this.bucket = properties.getBucket();
	}

	public String getObjectUrl(String object) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
		clearObject(object);
		return client.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().bucket(bucket).object(object).method(Method.GET).expiry(2, TimeUnit.HOURS).build());
	}

	public List<String> getObjectUrl(List<String> objects) {
		return objects.stream()
			.map(this::clearObject)
			.map(object -> {
				object = clearObject(object);
				try {
					return client.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().bucket(bucket).object(object).method(Method.GET).expiry(1, TimeUnit.DAYS).build());
				} catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException | InvalidResponseException | IOException | NoSuchAlgorithmException | XmlParserException | ServerException e) {
					log.error("文件存储服务异常", e);
					// throw new UnknownBusinessException("文件存储服务异常");
					return "unknown";
				}
			}).collect(Collectors.toList());
	}

	public List<String> getUploadObjectUrl(List<String> objects) {
		return objects.stream()

			.map(object -> {
				object = clearObject(object);
				try {
					return client.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().bucket(bucket).object(object).method(Method.PUT).expiry(2, TimeUnit.HOURS).build());
				} catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException | InvalidResponseException | IOException | NoSuchAlgorithmException | XmlParserException | ServerException e) {
					log.error("获取文件上传url失败", e);
					return "unknown";
				}
			}).collect(Collectors.toList());
	}

	public List<List<String>> getUploadTemporaryObjectUrl(Integer size) {
		return IntStream.range(0, size).parallel().mapToObj(x -> {
			String object = TEMPORARY_PREFIX.concat(IdUtil.objectId());
			try {
				return Arrays.asList(object, client.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().bucket(bucket).object(object).method(Method.PUT).expiry(2, TimeUnit.HOURS).build()));
			} catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException | InvalidResponseException | IOException | NoSuchAlgorithmException | XmlParserException | ServerException e) {
				log.error("文件存储服务异常", e);
				return Arrays.asList(object, "unknown");
			}
		}).collect(Collectors.toList());
	}

	private String clearObject(String object) {
		if (object.startsWith("/")) {
			object = object.replaceFirst("/", "");
		}
		return object;
	}
}
