package com.hfhk.system.service.modules.file2;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/File2")
public class File2Api {
	private final File2Service file2Service;

	public File2Api(File2Service file2Service) {
		this.file2Service = file2Service;
	}

	@PostMapping("/Get/Url")
	public List<String> getUrl(@RequestBody List<String> objects) {
		return file2Service.getObjectUrl(objects);
	}

	@PostMapping("/Upload/Url")
	public List<String> uploadUrl(@RequestBody List<String> objects) {
		return file2Service.getUploadObjectUrl(objects);
	}

	@PostMapping("/Upload/TemporaryUrl")
	public List<List<String>> temporaryUrl(@RequestParam(defaultValue = "1") Integer size) {
		return file2Service.getUploadTemporaryObjectUrl(size);
	}
}
