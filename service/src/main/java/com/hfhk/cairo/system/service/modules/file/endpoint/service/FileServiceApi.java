package com.hfhk.cairo.system.service.modules.file.endpoint.service;

import com.hfhk.cairo.core.page.Page;
import com.hfhk.cairo.security.oauth2.server.resource.authentication.CairoAuthenticationToken;
import com.hfhk.cairo.system.file.domain.File;
import com.hfhk.cairo.system.service.modules.file.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/service/file")
public class FileServiceApi {
	private final FileService fileService;

	public FileServiceApi(FileService fileService) {
		this.fileService = fileService;
	}

	@PostMapping("/upload")
	@PreAuthorize("isAuthenticated()")
	public List<File> upload(@AuthenticationPrincipal CairoAuthenticationToken token,
							 @RequestParam(defaultValue = "/") String folderPath,
							 @RequestPart Collection<MultipartFile> files) {
		log.debug("[api][file][token][client] : {}", token.getClient());
		log.debug("[api][file][token][user] : {}", token.getUser());
		return fileService.store(token.getClient().getId(), Optional.ofNullable(token.getUser().getUid()).orElse("admin"), folderPath, files);
	}

	@DeleteMapping("/delete")
	@PreAuthorize("isAuthenticated()")
	public void delete(@AuthenticationPrincipal CairoAuthenticationToken token,
					   @RequestParam String[] fileIds) {
		log.debug("[api][file][delete][token][client] : {}", token.getClient());
		log.debug("[api][file][delete][token][user] : {}", token.getUser());
		log.debug("[api][file][delete][fileIds]: {}", Arrays.asList(fileIds));
		fileService.delete(token.getClient().getId(), token.getUser().getUid(), Arrays.asList(fileIds));
	}

	@GetMapping("/find")
	@PreAuthorize("isAuthenticated()")
	public List<File> find(
		@AuthenticationPrincipal CairoAuthenticationToken token,
		@RequestParam(required = false) String folderPath,
		@RequestParam(required = false) String filename) {
		return fileService.find(token.getClient().getId(), folderPath, filename);
	}

	@GetMapping("/find_page")
	@PreAuthorize("isAuthenticated()")
	public Page<File> pageFind(
		@AuthenticationPrincipal CairoAuthenticationToken token,
		@PageableDefault Pageable pageable,
		@RequestParam(required = false) String folderPath,
		@RequestParam(required = false) String filename) {
		return fileService.pageFind(token.getClient().getId(), pageable, folderPath, filename);
	}
}
