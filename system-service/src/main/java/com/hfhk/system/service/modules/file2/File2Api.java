package com.hfhk.system.service.modules.file2;

import io.minio.errors.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/File2")
public class File2Api {
	private final File2Service file2Service;

	public File2Api(File2Service file2Service) {
		this.file2Service = file2Service;
	}
	
	@GetMapping("/Upload/Url")
	public String url(@RequestParam String object) throws IOException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, ServerException, ErrorResponseException, XmlParserException, InsufficientDataException, InternalException {
		return file2Service.getUploadObjectUrl(object);
	}

	@GetMapping("/Upload/TemporaryUrl")
	public List<String> temporaryUrl(@RequestParam String filename) throws IOException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, ServerException, ErrorResponseException, XmlParserException, InsufficientDataException, InternalException {
		return file2Service.getUploadTemporaryObjectUrl(filename);
	}
}
