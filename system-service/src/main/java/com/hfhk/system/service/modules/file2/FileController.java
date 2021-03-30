package com.hfhk.system.service.modules.file2;

import io.minio.errors.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Controller
@RequestMapping("/File2")
public class FileController {
	private final File2Service file2Service;

	public FileController(File2Service file2Service) {
		this.file2Service = file2Service;
	}

	@GetMapping("/Access")
	public ModelAndView get(@RequestParam String object) throws IOException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, ServerException, ErrorResponseException, XmlParserException, InsufficientDataException, InternalException {
		return new ModelAndView(new RedirectView(file2Service.getObjectUrl(object)));
	}
}
