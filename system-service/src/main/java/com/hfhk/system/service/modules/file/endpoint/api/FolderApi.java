package com.hfhk.system.service.modules.file.endpoint.api;

import com.hfhk.cairo.core.page.Page;
import com.hfhk.cairo.security.oauth2.user.AuthPrincipal;
import com.hfhk.cairo.starter.service.web.handler.BusinessResult;
import com.hfhk.system.file.domain.Folder;
import com.hfhk.system.file.domain.request.FolderPageFindRequest;
import com.hfhk.system.service.modules.file.service.FolderService;
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
	@PreAuthorize("isAuthenticated()")
	@BusinessResult
	public void save(@AuthenticationPrincipal AuthPrincipal principal, String path) {
		folderService.create(principal.getClient(), path);
	}

	@PutMapping
	@PreAuthorize("isAuthenticated()")
	@BusinessResult
	public void put(@AuthenticationPrincipal AuthPrincipal principal, String path, String newPath) {
		folderService.rename(principal.getClient(), path, newPath);
	}

	@DeleteMapping
	@PreAuthorize("isAuthenticated()")
	@BusinessResult
	public void delete(@AuthenticationPrincipal AuthPrincipal principal,
					   String path) {
		folderService.delete(principal.getClient(), path);
	}

	@GetMapping("/find")
	@PreAuthorize("isAuthenticated()")
	@BusinessResult
	public Page<String> find(
		@AuthenticationPrincipal AuthPrincipal principal,
		@RequestBody FolderPageFindRequest request) {
		return folderService.pageFind(principal.getClient(), request);
	}

	@GetMapping("/find_tree")
	@PreAuthorize("isAuthenticated()")
	@BusinessResult
	public List<Folder> treeFind(@AuthenticationPrincipal AuthPrincipal token, String path) {
		return folderService.treeFind(token.getClient(), path);
	}
}
