package com.hfhk.cairo.system.service.modules.file.endpoint.service;

import com.hfhk.cairo.core.page.Page;
import com.hfhk.cairo.security.oauth2.server.resource.authentication.CairoAuthenticationToken;
import com.hfhk.cairo.system.file.domain.Folder;
import com.hfhk.cairo.system.service.modules.file.service.FolderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
	public Page<String> find(@AuthenticationPrincipal CairoAuthenticationToken token,
							 @PageableDefault Pageable pageable, String folderPath) {
		return folderService.pageFind(token.getClient().getId(), pageable, folderPath);
	}

	@GetMapping("/find_tree")
	@PreAuthorize("isAuthenticated()")
	public List<Folder> treeFind(@AuthenticationPrincipal CairoAuthenticationToken token, String folderPath) {
		return folderService.treeFind(token.getClient().getId(), folderPath);
	}

	@PostMapping("/create")
	@PreAuthorize("isAuthenticated()")
	public void save(@AuthenticationPrincipal CairoAuthenticationToken token, String folderPath) {
		folderService.create(token.getClient().getId(), folderPath);
	}

	@PutMapping("/rename")
	@PreAuthorize("isAuthenticated()")
	public void put(@AuthenticationPrincipal CairoAuthenticationToken token, String folderPath, String newFolderPath) {
		folderService.rename(token.getClient().getId(), folderPath, newFolderPath);
	}

	@DeleteMapping("/delete")
	@PreAuthorize("isAuthenticated()")
	public void delete(@AuthenticationPrincipal CairoAuthenticationToken token, String folderPath) {
		log.debug("[folder][delete][token][client]: [{}]", token.getClient());
		log.debug("[folder][delete][token][user]: [{}]", token.getUser());
		log.debug("[folder][delete][folderPath]: [{}]", folderPath);

		folderService.delete(token.getClient().getId(), folderPath);
	}

}
