package com.hfhk.system.service.modules.file;

import com.hfhk.cairo.core.page.Page;
import com.hfhk.cairo.security.oauth2.user.AuthPrincipal;
import com.hfhk.system.modules.file.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/File/Folder")
public class FolderApi {
	private final FolderService folderService;

	public FolderApi(FolderService folderService) {
		this.folderService = folderService;
	}

	@PostMapping("/Save")
	@PreAuthorize("isAuthenticated()")
	public Optional<Folder> save(@AuthenticationPrincipal AuthPrincipal principal, @RequestBody FolderSaveParam param) {
		String client = principal.getClient();
		return folderService.save(client, param);
	}

	@PatchMapping("/Rename")
	@PreAuthorize("isAuthenticated()")
	public Optional<Folder> put(@AuthenticationPrincipal AuthPrincipal principal, @RequestBody FolderRenameParam param) {
		String client = principal.getClient();
		return folderService.rename(client, param);
	}

	@DeleteMapping("/Delete")
	@PreAuthorize("isAuthenticated()")
	public List<Folder> delete(@AuthenticationPrincipal AuthPrincipal principal, @RequestBody FolderDeleteParam param) {
		String client = principal.getClient();
		return folderService.delete(client, param);
	}

	@PostMapping("/Find")
	@PreAuthorize("isAuthenticated()")
	public Page<Folder> find(@AuthenticationPrincipal AuthPrincipal principal, @RequestBody FolderFindParam param) {
		String client = principal.getClient();
		return folderService.findPage(client, param);
	}

	@PostMapping("/FindPage")
	@PreAuthorize("isAuthenticated()")
	public Page<Folder> findPage(@AuthenticationPrincipal AuthPrincipal principal, @RequestBody FolderFindParam param) {
		String client = principal.getClient();
		return folderService.findPage(client, param);
	}

	@PostMapping("/FindTree")
	@PreAuthorize("isAuthenticated()")
	public List<Folder> treeFind(@AuthenticationPrincipal AuthPrincipal principal, @RequestBody FolderFindParam param) {
		String client = principal.getClient();
		return folderService.findTree(client, param);
	}
}
