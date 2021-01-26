package com.hfhk.system.service.modules.file;


import com.hfhk.auth.modules.user.User;
import com.hfhk.auth.modules.user.UserClientCredentialsClient;
import com.hfhk.cairo.core.exception.BusinessException;
import com.hfhk.cairo.core.page.Page;
import com.hfhk.cairo.mongo.data.Created;
import com.hfhk.cairo.mongo.data.LastModified;
import com.hfhk.system.modules.file.File;
import com.hfhk.system.modules.file.FileDeleteParam;
import com.hfhk.system.modules.file.FileFindParam;
import com.hfhk.system.service.domain.mongo.HfhkMongoProperties;
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
import java.util.*;
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
					mongoTemplate.updateFirst(query, update, FileMongo.class, mongoProperties.COLLECTION.file());
					return id;
				} catch (IOException e) {
					throw new BusinessException(FileBusiness.UploadField, e);
				}
			})
			.collect(Collectors.toList());
		Criteria criteria = Criteria.where(FileMongo.FIELD._ID).in(ids);
		Query query = Query.query(criteria);
		List<FileMongo> contents = mongoTemplate.find(query, FileMongo.class, mongoProperties.COLLECTION.file());
		return buildFiles(contents);
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
	 * @param param  param
	 * @return file list
	 */
	List<File> delete(String client, String uid, FileDeleteParam param) {
		Criteria criteria = Criteria.where(FileMongo.FIELD.CLIENT).is(client).and(FileMongo.FIELD._ID).in(param);
		Query query = Query.query(criteria);
		List<FileMongo> contents = mongoTemplate.find(query, FileMongo.class, mongoProperties.COLLECTION.file());
		List<File> files = buildFiles(contents);
		gridFsTemplate.delete(Query.query(criteria));
		return files;
	}


	/**
	 * 文件查询
	 *
	 * @param client client
	 * @param param  param
	 * @return file list
	 */
	List<File> find(String client, FileFindParam param) {
		Criteria criteria = buildCriteria(client, param);

		Query query = Query.query(criteria);
		List<FileMongo> contents = mongoTemplate.find(query, FileMongo.class, mongoProperties.COLLECTION.file());
		return buildFiles(contents);

	}


	/**
	 * 文件分页查询
	 *
	 * @param client client
	 * @param param  param
	 * @return file list page
	 */
	Page<File> findPage(String client, FileFindParam param) {
		Criteria criteria = buildCriteria(client, param);
		Query query = Query.query(criteria);
		long total = mongoTemplate.count(query, FileMongo.class, mongoProperties.COLLECTION.file());
		query.with(param.pageable());

		List<FileMongo> contents = mongoTemplate.find(query, FileMongo.class, mongoProperties.COLLECTION.file());
		List<File> files = buildFiles(contents);
		return new Page<>(param, files, total);
	}

	List<File> buildFiles(List<FileMongo> contents) {
		Set<String> uids = contents.stream().flatMap(x -> Stream.of(x.getMetadata().getCreated().getUid(), x.getMetadata().getLastModified().getUid()))
			.filter(Objects::nonNull)
			.collect(Collectors.toSet());
		Map<String, User> userMap = userClient.findMap(uids);

		return contents
			.stream()
			.map(x -> FileConverter.fileMapper(x, userMap.get(x.getMetadata().getCreated().getUid()), userMap.get(x.getMetadata().getLastModified().getUid())))
			.collect(Collectors.toList());
	}

	private Criteria buildCriteria(String client, FileFindParam param) {
		Criteria criteria = Criteria.where(FileMongo.FIELD.CLIENT).is(client);
		Optional.ofNullable(param.getIds()).filter(x -> !x.isEmpty()).ifPresent(ids -> criteria.and(FileMongo.FIELD._ID).in(ids));
		Optional.of(
			Optional.ofNullable(param.getItems())
				.filter(x -> !x.isEmpty())
				.map(items -> items.stream()
					.map(item -> Criteria
						.where(FileMongo.FIELD.PATH).regex(item.getPath())
						.and(FileMongo.FIELD.FILENAME).regex(item.getFilename()))
					.toArray(Criteria[]::new))
				.orElseGet(() -> new Criteria[]{})
		)
			.filter(x -> x.length > 0)
			.ifPresent(criteria::orOperator);
		return criteria;
	}


}
