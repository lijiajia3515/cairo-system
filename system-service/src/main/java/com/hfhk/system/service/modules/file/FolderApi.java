package com.hfhk.system.service.modules.file;

import com.hfhk.cairo.core.page.Page;
import com.hfhk.cairo.security.oauth2.user.AuthPrincipal;
import com.hfhk.system.file.domain.Folder;
import com.hfhk.system.file.domain.FolderDeleteParam;
import com.hfhk.system.file.domain.FolderFindParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/File/Folder")
public class FolderApi {
	private final FolderService folderService;

	public FolderApi(FolderService folderService) {
		this.folderService = folderService;
	}

	@PostMapping("/Save")
	@PreAuthorize("isAuthenticated()")
	public void save(@AuthenticationPrincipal AuthPrincipal principal, String path) {
		String client = principal.getClient();
		folderService.save(client, path);
	}

	@PatchMapping("/Rename")
	@PreAuthorize("isAuthenticated()")
	public void put(@AuthenticationPrincipal AuthPrincipal principal, String path, String newPath) {
		String client = principal.getClient();
		folderService.rename(client, path, newPath);
	}

	@DeleteMapping("/Delete")
	@PreAuthorize("isAuthenticated()")
	public void delete(@AuthenticationPrincipal AuthPrincipal principal, @RequestBody FolderDeleteParam param) {
		String client = principal.getClient();
		folderService.delete(client, param);
	}

	@GetMapping("/Find")
	@PreAuthorize("isAuthenticated()")
	public Page<String> find(@AuthenticationPrincipal AuthPrincipal principal, @RequestBody FolderFindParam param) {
		String client = principal.getClient();
		return folderService.findPage(client, param);
	}

	@GetMapping("/FindTree")
	@PreAuthorize("isAuthenticated()")
	public List<Folder> treeFind(@AuthenticationPrincipal AuthPrincipal principal, @RequestBody FolderFindParam param) {
		String client = principal.getClient();
		return folderService.findTree(client, param);
	}
}
