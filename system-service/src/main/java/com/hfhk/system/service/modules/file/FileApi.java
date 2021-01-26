package com.hfhk.system.service.modules.file;

import cn.hutool.core.net.URLEncoder;
import com.hfhk.cairo.core.CoreConstants;
import com.hfhk.cairo.core.page.Page;
import com.hfhk.cairo.security.oauth2.user.AuthPrincipal;
import com.hfhk.system.modules.file.File;
import com.hfhk.system.modules.file.FileDeleteParam;
import com.hfhk.system.modules.file.FileFindParam;
import org.apache.commons.io.IOUtils;
import org.springframework.data.mongodb.gridfs.GridFsResource;
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
 * file api
 */
@RestController
@RequestMapping("/File")
public class FileApi {

	private final FileService fileService;

	public FileApi(FileService fileService) {
		this.fileService = fileService;
	}

	/**
	 * 上传文件
	 *
	 * @param principal principal
	 * @param path      path
	 * @param files     文件
	 * @return 1
	 */
	@PostMapping("/Upload")
	@PreAuthorize("isAuthenticated()")
	public List<File> upload(@AuthenticationPrincipal AuthPrincipal principal,
							 @RequestParam(name = "Path", defaultValue = FileConstant.TEMPORARY_FILE_PATH) String path,
							 @RequestPart(name = "Files") Collection<MultipartFile> files) {
		String client = principal.getClient();
		return fileService.store(client, principal.getUser().getUid(), path, files);
	}

	/**
	 * 匿名 文件上传
	 *
	 * @param files 文件
	 * @return x
	 */
	@PostMapping("/UploadTemporary")
	@PreAuthorize("isAuthenticated()")
	public List<File> temporaryUpload(
		@AuthenticationPrincipal AuthPrincipal principal, @RequestPart(name = "Files") Collection<MultipartFile> files) {
		String client = principal.getClient();
		String path = FileConstant.TEMPORARY_FILE_PATH.concat("/").concat(CoreConstants.SNOWFLAKE.nextIdStr());
		return fileService.store(client, FileConstant.ANONYMOUS_UID, path, files);
	}

	@DeleteMapping
	@PreAuthorize("isAuthenticated()")
	public List<File> delete(@AuthenticationPrincipal AuthPrincipal principal, @RequestBody FileDeleteParam param) {
		String client = principal.getClient();
		return fileService.delete(client, FileConstant.ANONYMOUS_UID, param);
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


	@PostMapping("/Find")
	@PreAuthorize("isAuthenticated()")
	public List<File> find(@AuthenticationPrincipal AuthPrincipal principal, @RequestBody FileFindParam param) {
		String client = principal.getClient();
		return fileService.find(client, param);
	}

	@PostMapping("/FindPage")
	@PreAuthorize("isAuthenticated()")
	public Page<File> findPage(@AuthenticationPrincipal AuthPrincipal principal, @RequestBody FileFindParam param) {
		String client = principal.getClient();
		return fileService.findPage(client, param);
	}


}
