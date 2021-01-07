package com.hfhk.system.service.modules.file;

import com.hfhk.auth.client.UserClientCredentialsClient;
import com.hfhk.auth.domain.Metadata;
import com.hfhk.cairo.core.exception.BusinessException;
import com.hfhk.cairo.core.page.Page;
import com.hfhk.cairo.mongo.data.Created;
import com.hfhk.cairo.mongo.data.LastModified;
import com.hfhk.system.file.domain.File;
import com.hfhk.system.file.domain.FileFindParam;
import com.hfhk.system.service.constants.HfhkMongoProperties;
import com.hfhk.system.service.domain.mongo.FileMongo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * File Service
 */
@Slf4j
@Service
public class FileService {

	private final HfhkMongoProperties mongoProperties;
	private final MongoTemplate mongoTemplate;

	private final GridFsTemplate gridFsTemplate;

	private final UserClientCredentialsClient userClient;

	public FileService(HfhkMongoProperties mongoProperties,
					   MongoTemplate mongoTemplate,
					   GridFsTemplate gridFsTemplate,
					   UserClientCredentialsClient userClient) {
		this.mongoProperties = mongoProperties;
		this.mongoTemplate = mongoTemplate;
		this.gridFsTemplate = gridFsTemplate;
		this.userClient = userClient;
	}

	/**
	 * 文件上传
	 *
	 * @param client client
	 * @param uid    uid
	 * @param path   path
	 * @param files  files
	 * @return file list
	 */
	List<File> store(String client, String uid, String path, Collection<MultipartFile> files) {
		List<String> ids = files
			.parallelStream()
			.map(file -> {
				String filename = file.getOriginalFilename();
				String contentType = file.getContentType();

				Created created = Created.builder().uid(uid).at(LocalDateTime.now()).build();
				LastModified lastModified = LastModified.builder().uid(uid).at(LocalDateTime.now()).build();

				try (InputStream in = file.getInputStream()) {
					String id = gridFsTemplate.store(in, filename, contentType, null).toString();
					Query query = Query.query(Criteria.where(FileMongo.FIELD._ID).is(id));
					Update update = new Update()
						.set(FileMongo.FIELD.CLIENT, client)
						.set(FileMongo.FIELD.PATH, path)
						.set(FileMongo.FIELD.METADATA.CREATED.SELF, created)
						.set(FileMongo.FIELD.METADATA.LAST_MODIFIED.SELF, lastModified);
					mongoTemplate.updateFirst(query, update, FileMongo.class, mongoProperties.COLLECTION.FILE);
					return id;
				} catch (IOException e) {
					throw new BusinessException(FileBusiness.UploadField, e);
				}
			})
			.collect(Collectors.toList());
		Criteria criteria = Criteria.where(FileMongo.FIELD._ID).in(ids);
		Query query = Query.query(criteria);
		List<FileMongo> contents = mongoTemplate.find(query, FileMongo.class, mongoProperties.COLLECTION.FILE);
		Set<String> uids = contents.stream()
			.flatMap(x -> Stream.of(x.getMetadata().getCreated().getUid(), x.getMetadata().getLastModified().getUid()))
			.collect(Collectors.toSet());
		// userClient.find
		return null;
	}

	/**
	 * 文件资源读取
	 *
	 * @param id id
	 * @return x
	 */
	Optional<GridFsResource> findResource(String id) {
		return Optional.ofNullable(gridFsTemplate.findOne(Query.query(Criteria.where(FileMongo.FIELD._ID).is(id))))
			.map(gridFsTemplate::getResource);

	}

	/**
	 * 删除
	 *
	 * @param client client
	 * @param uid    uid
	 * @param ids    ids
	 */
	void delete(String client, String uid, List<String> ids) {
		if (!ids.isEmpty()) {
			Criteria criteria = Criteria
				.where(FileMongo.FIELD.CLIENT).is(client)
				.and(FileMongo.FIELD._ID).in(ids);
			gridFsTemplate.delete(Query.query(criteria));
		}
	}


	/**
	 * 文件查询
	 *
	 * @param client client
	 * @param param  param
	 * @return file list
	 */
	List<File> find(String client, FileFindParam param) {
		Criteria criteria = Criteria.where(FileMongo.FIELD.CLIENT).is(client);
		Optional.ofNullable(param)
			.ifPresent(x -> {
				Optional.ofNullable(x.getPath()).ifPresent(y -> criteria.and(FileMongo.FIELD.PATH).regex(y));
				Optional.ofNullable(x.getFilename()).ifPresent(y -> criteria.and(FileMongo.FIELD.FILENAME).regex(y));
			});

		Query query = Query.query(criteria);
		return mongoTemplate.find(query, FileMongo.class, mongoProperties.COLLECTION.FILE)
			.stream()
			.flatMap(x -> optionalFile(x).stream())
			.collect(Collectors.toList());

	}


	/**
	 * 文件分页查询
	 *
	 * @param client client
	 * @param param  param
	 * @return file list page
	 */
	Page<File> findPage(String client, FileFindParam param) {
		Criteria criteria = Criteria.where(FileMongo.FIELD.CLIENT).is(client);

		Optional.ofNullable(param.getPath()).ifPresent(x -> criteria.and(FileMongo.FIELD.PATH).regex(x));
		Optional.ofNullable(param.getFilename()).ifPresent(x -> criteria.and(FileMongo.FIELD.FILENAME).regex(x));
		Query query = Query.query(criteria);
		long total = mongoTemplate.count(query, FileMongo.class);
		query.with(param.pageable());

		List<FileMongo> contents = mongoTemplate.find(query, FileMongo.class);
		Set<String> createdUids = contents.stream().map(x -> x.getMetadata().getCreated().getUid()).collect(Collectors.toSet());
		Set<String> lastModifiedUids = contents.stream().map(x -> x.getMetadata().getLastModified().getUid()).collect(Collectors.toSet());
//		userClient
//		List<File> files = mongoTemplate.find(query, FileMongo.class).stream()
//			.map(x -> FileConverter.fileMapper(x)).collect(Collectors.toList());
//		return new Page<>(param, files, total);
		return null;
	}


}
