package com.hfhk.system.service.modules.file.service;

import com.hfhk.cairo.core.page.Page;
import com.hfhk.system.file.domain.File;
import com.hfhk.system.file.domain.request.FilePageFindRequest;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface FileService {

	/**
	 * 文件上传
	 *
	 * @param client     client
	 * @param uid        uid
	 * @param folderPath folderPath
	 * @param files      files
	 * @return file list
	 */
	List<File> store(String client, String uid, String folderPath, Collection<MultipartFile> files);

	/**
	 * 文件资源读取
	 *
	 * @param id id
	 * @return x
	 */
	Optional<GridFsResource> findResource(String id);

	/**
	 * 删除
	 *
	 * @param client client
	 * @param uid    uid
	 * @param ids    ids
	 */
	void delete(String client, String uid, List<String> ids);


	/**
	 * 文件查询
	 *
	 * @param client   client
	 * @param filepath filepath
	 * @param filename filename
	 * @return file list page
	 */
	List<File> find(String client, String filepath, String filename);


	/**
	 * 文件分页查询
	 *
	 * @param client  client
	 * @param request request
	 * @return file list page
	 */
	Page<File> pageFind(String client, FilePageFindRequest request);
}
