package com.hfhk.system.service.modules.file.endpoint.service;

import com.hfhk.cairo.core.page.Page;
import com.hfhk.cairo.security.oauth2.user.AuthPrincipal;
import com.hfhk.system.file.domain.Folder;
import com.hfhk.system.file.domain.request.FolderPageFindRequest;
import com.hfhk.system.service.modules.file.service.FolderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j

@RestController
@RequestMapping("/service/folder")
public class FolderServiceApi {
	private final FolderService folderService;

	public FolderServiceApi(FolderService folderService) {
		this.folderService = folderService;
	}

	@GetMapping("/find")
	@PreAuthorize("isAuthenticated()")
	public Page<String> find(@AuthenticationPrincipal AuthPrincipal principal,
							 @RequestBody FolderPageFindRequest request) {
		return folderService.pageFind(principal.getClient(), request);
	}

	@GetMapping("/find_tree")
	@PreAuthorize("isAuthenticated()")
	public List<Folder> treeFind(@AuthenticationPrincipal AuthPrincipal principal, String folderPath) {
		return folderService.treeFind(principal.getClient(), folderPath);
	}

	@PostMapping("/create")
	@PreAuthorize("isAuthenticated()")
	public void save(@AuthenticationPrincipal AuthPrincipal principal, String folderPath) {
		folderService.create(principal.getClient(), folderPath);
	}

	@PutMapping("/rename")
	@PreAuthorize("isAuthenticated()")
	public void put(@AuthenticationPrincipal AuthPrincipal principal, String folderPath, String newFolderPath) {
		folderService.rename(principal.getClient(), folderPath, newFolderPath);
	}

	@DeleteMapping("/delete")
	@PreAuthorize("isAuthenticated()")
	public void delete(@AuthenticationPrincipal AuthPrincipal principal, String folderPath) {
		folderService.delete(principal.getClient(), folderPath);
	}

}
