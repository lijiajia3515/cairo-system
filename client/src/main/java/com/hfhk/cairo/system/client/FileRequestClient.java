package com.hfhk.cairo.system.client;

import com.hfhk.cairo.core.page.Page;
import com.hfhk.cairo.system.file.domain.File;
import com.hfhk.cairo.system.file.domain.Folder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "service_system_v1", path = "/service", contextId = "serviceSystemV1FileRequestClient")
public interface FileRequestClient {

	/**
	 * 查找文件夹
	 *
	 * @param folderPath 路径
	 * @return folder tree list
	 */
	@GetMapping(path = "/folder/find_tree")
	List<Folder> treeFindFolder(@RequestParam(name = "folderPath") String folderPath);

	/**
	 * 创建文件夹
	 *
	 * @param folderPath 文件夹路径
	 */
	@PostMapping(path = "/folder/create")
	void createFolder(@RequestParam(name = "folderPath") String folderPath);

	/**
	 * 重名
	 *
	 * @param folderPath    路径
	 * @param newFolderPath 新路径
	 */
	@PutMapping(path = "/folder/rename")
	void renameFolder(@RequestParam(name = "folderPath") String folderPath, @RequestParam(name = "newFolderPath") String newFolderPath);

	/**
	 * 删除 文件夹
	 *
	 * @param folderPath 路径
	 */
	@DeleteMapping(path = "/folder/delete")
	void deleteFolder(@RequestParam(name = "folderPath") String folderPath);


	// file divide line

	@PostMapping(path = "/file/upload", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	List<File> uploadFile(@RequestParam(name = "folderPath", defaultValue = "/") String folderPath,
						  @RequestPart(name = "files") MultipartFile[] files);

	/**
	 * 删除 文件夹
	 *
	 * @param fileIds 文件id
	 */
	@DeleteMapping(path = "/file/delete")
	void deleteFile(@RequestParam(name = "fileIds") String[] fileIds);

	/**
	 * 文件 查询(分页)
	 *
	 * @param page       page
	 * @param folderPath folderPath
	 * @param filename   filename
	 * @return file list page
	 */
	@GetMapping(path = "/file/find_page")
	Page<File> pageFindFile(Pageable page, @RequestParam("folderPath") String folderPath, @RequestParam("filename") String filename);


	/**
	 * 文件查询
	 *
	 * @param folderPath folderPath
	 * @param filename   filename
	 * @return file list
	 */
	@GetMapping(path = "/file/find")
	List<File> findFile(@RequestParam("folderPath") String folderPath, @RequestParam("filename") String filename);

}
