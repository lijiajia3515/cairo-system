package com.hfhk.cairo.system.service.modules.file.endpoint.api;

import com.hfhk.cairo.core.page.Page;
import com.hfhk.cairo.security.oauth2.server.resource.authentication.CairoAuthenticationToken;
import com.hfhk.cairo.starter.web.handler.StatusResult;
import com.hfhk.cairo.system.file.domain.Folder;
import com.hfhk.cairo.system.service.modules.file.service.FolderService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/folder")
public class FolderApi {
	private final FolderService folderService;

	public FolderApi(FolderService folderService) {
		this.folderService = folderService;
	}

	@PostMapping
	@StatusResult
	@PreAuthorize("isAuthenticated()")
	public void save(@AuthenticationPrincipal CairoAuthenticationToken token, String path) {
		folderService.create(token.getClient().getId(), path);
	}

	@PutMapping
	@StatusResult
	@PreAuthorize("isAuthenticated()")
	public void put(@AuthenticationPrincipal CairoAuthenticationToken token, String path, String newPath) {
		folderService.rename(token.getClient().getId(), path, newPath);
	}

	@DeleteMapping
	@StatusResult
	@PreAuthorize("isAuthenticated()")
	public void delete(@AuthenticationPrincipal CairoAuthenticationToken token, String path) {
		folderService.delete(token.getClient().getId(), path);
	}

	@GetMapping("/find")
	@StatusResult
	@PreAuthorize("isAuthenticated()")
	public Page<String> find(
		@AuthenticationPrincipal CairoAuthenticationToken token,
		@PageableDefault Pageable pageable, String path) {
		return folderService.pageFind(token.getClient().getId(), pageable, path);
	}

	@GetMapping("/find_tree")
	@StatusResult
	@PreAuthorize("isAuthenticated()")
	public List<Folder> treeFind(@AuthenticationPrincipal CairoAuthenticationToken token, String path) {
		return folderService.treeFind(token.getClient().getId(), path);
	}
}
