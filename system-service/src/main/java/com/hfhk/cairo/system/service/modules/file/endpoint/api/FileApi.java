package com.hfhk.cairo.system.service.modules.file.endpoint.api;

import cn.hutool.core.net.URLEncoder;
import cn.hutool.core.util.IdUtil;
import com.hfhk.cairo.core.page.Page;
import com.hfhk.cairo.security.oauth2.server.resource.authentication.CairoAuthenticationToken;
import com.hfhk.cairo.starter.web.handler.StatusResult;
import com.hfhk.cairo.system.file.domain.File;
import com.hfhk.cairo.system.service.modules.file.service.FileService;
import org.apache.commons.io.IOUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;

/**
 * 文件api
 */
@RestController
@RequestMapping("/file")
public class FileApi {
	private final FileService fileService;

	public FileApi(FileService fileService) {
		this.fileService = fileService;
	}

	/**
	 * 上传文件
	 *
	 * @param token      token
	 * @param folderPath 文件路径
	 * @param files      文件
	 * @return 1
	 */
	@PostMapping("/upload")
	@PreAuthorize("isAuthenticated()")
	@StatusResult
	public List<File> upload(@AuthenticationPrincipal CairoAuthenticationToken token,
							 @RequestParam(defaultValue = "/") String folderPath,
							 @RequestPart Collection<MultipartFile> files) {
		return fileService.store(token.getClient().getId(), token.getUser().getUid(), folderPath, files);
	}

	/**
	 * 匿名 文件上传
	 *
	 * @param files 文件
	 * @return x
	 */
	@PostMapping("/upload_temporary")
	@PermitAll
	@StatusResult
	public List<File> temporaryUpload(
		@RequestPart Collection<MultipartFile> files) {
		String folderPath = "/temporary/" + IdUtil.fastSimpleUUID();
		return fileService.store("anonymous", "anonymous", folderPath, files);
	}

	/**
	 * 文件流
	 *
	 * @param id       id
	 * @param filename filename
	 * @param response response
	 * @throws IOException x
	 */
	@GetMapping("/{id}/{filename}")
	@PermitAll
	public void get(@PathVariable String id, @PathVariable String filename, HttpServletResponse response) throws IOException {
		GridFsResource resource = fileService.findResource(id).orElse(null);

		if (resource != null) {
			response.setContentType(resource.getContentType());
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"" + URLEncoder.ALL.encode(resource.getFilename(), StandardCharsets.UTF_8) + "\"");
			IOUtils.copy(resource.getInputStream(), response.getOutputStream());
		}
	}

	@GetMapping("/find")
	@PreAuthorize("isAuthenticated()")
	@StatusResult
	public List<File> find(
		@AuthenticationPrincipal CairoAuthenticationToken token,
		@RequestParam(required = false) String filepath,
		@RequestParam(required = false) String filename) {
		return fileService.find(token.getClient().getId(), filepath, filename);
	}

	@GetMapping("/find_page")
	@PreAuthorize("isAuthenticated()")
	@StatusResult
	public Page<File> pageFind(
		@AuthenticationPrincipal CairoAuthenticationToken token,
		@PageableDefault Pageable pageable,
		@RequestParam(required = false) String filepath,
		@RequestParam(required = false) String filename) {
		return fileService.pageFind(token.getClient().getId(), pageable, filepath, filename);
	}


}
