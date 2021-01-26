package com.hfhk.system.modules.file.client;

import com.hfhk.cairo.core.page.Page;
import com.hfhk.system.modules.file.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "service-system-v1", path = "/File", contextId = "serviceSystemV1-file-request-client")
public interface FileRequestClient {

	/**
	 * 创建文件夹
	 *
	 * @param param param
	 */
	@PostMapping(path = "/Folder/Save")
	void createFolder(@RequestBody FolderSaveParam param);

	/**
	 * 重名
	 *
	 * @param param param
	 */
	@PutMapping(path = "/Folder/Rename")
	void renameFolder(@RequestBody FolderRenameParam param);

	/**
	 * 删除 文件夹
	 *
	 * @param param param
	 */
	@DeleteMapping(path = "/Folder/Delete")
	void deleteFolder(@RequestBody FolderDeleteParam param);

	/**
	 * 查找文件夹
	 *
	 * @param param param
	 * @return folder tree list
	 */
	@GetMapping(path = "/Folder/FindTree")
	List<Folder> treeFindFolder(@RequestBody FolderFindParam param);


	//file
	@PostMapping(path = "/Upload", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	List<File> uploadFile(@RequestParam(name = "Path", defaultValue = "/") String folderPath,
						  @RequestPart(name = "Files") MultipartFile[] files);

	/**
	 * 文件 查询(分页)
	 *
	 * @param param param
	 * @return file list page
	 */
	@PostMapping(path = "/FindPage")
	Page<File> pageFindFile(@RequestBody FileFindParam param);


	/**
	 * 文件查询
	 *
	 * @param param param
	 * @return file list
	 */
	@PostMapping(path = "/Find")
	List<File> findFile(@RequestBody FileFindParam param);

}
