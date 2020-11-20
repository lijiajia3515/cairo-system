package com.hfhk.cairo.system.service.modules.file.service.impl;

import com.hfhk.cairo.auth.client.UserOAuthClientCredentialsClient;
import com.hfhk.cairo.auth.domain.Metadata;
import com.hfhk.cairo.core.page.Page;
import com.hfhk.cairo.data.mongo.Created;
import com.hfhk.cairo.data.mongo.LastModified;
import com.hfhk.cairo.system.file.domain.File;
import com.hfhk.cairo.system.service.modules.file.domain.mongo.FileMongo;
import com.hfhk.cairo.system.service.modules.file.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j

@Service
public class
FileServiceImpl implements FileService {
	private final MongoTemplate mongoTemplate;
	private final GridFsTemplate gridFsTemplate;
	private final UserOAuthClientCredentialsClient userClient;

	public FileServiceImpl(MongoTemplate mongoTemplate, GridFsTemplate gridFsTemplate, UserOAuthClientCredentialsClient userClient) {
		this.mongoTemplate = mongoTemplate;
		this.gridFsTemplate = gridFsTemplate;
		this.userClient = userClient;
	}

	@Override
	public List<File> store(String client, String uid, String folderPath, Collection<MultipartFile> files) {
		return files
			.parallelStream()
			.flatMap(file -> {
				String filename = file.getOriginalFilename();
				String contentType = file.getContentType();

				Created created = Created.builder().uid(uid).at(LocalDateTime.now()).build();
				LastModified lastModified = LastModified.builder().uid(uid).at(LocalDateTime.now()).build();

				try (InputStream in = file.getInputStream()) {
					String id = gridFsTemplate.store(in, filename, contentType, null).toString();
					Query query = Query.query(Criteria.where("_id").is(id));
					Update update = new Update()
						.set("client", client)
						.set("folderPath", folderPath)
						.set("metadata.created", created)
						.set("metadata.lastModified", lastModified);
					mongoTemplate.updateFirst(query, update, FileMongo.class);
					return Optional.ofNullable(mongoTemplate.findById(id, FileMongo.class))
						.flatMap(this::optionalFile).stream();
				} catch (IOException e) {
					log.warn("file store error", e);
					return Stream.empty();
				}
			}).collect(Collectors.toList());
	}

	@Override
	public Optional<GridFsResource> findResource(String id) {
		return Optional.ofNullable(gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(id))))
			.map(gridFsTemplate::getResource);
	}

	@Override
	public void delete(String client, String uid, List<String> ids) {
		if (!ids.isEmpty()) {
			final Criteria criteria = Criteria.where("client").is(client).and("_id").in(ids);
			gridFsTemplate.delete(Query.query(criteria));
		}
	}

	@Override
	public List<File> find(String client, String filepath, String filename) {
		Criteria criteria = Criteria.where("client").is(client);

		Optional.ofNullable(filepath).ifPresent(x -> criteria.and("folderPath").regex(x));
		Optional.ofNullable(filename).ifPresent(x -> criteria.and("filename").regex(x));
		Query query = Query.query(criteria);
		return mongoTemplate.find(query, FileMongo.class).stream().flatMap(x -> optionalFile(x).stream()).collect(Collectors.toList());
	}

	@Override
	public Page<File> pageFind(String client, Pageable pageable, String filepath, String filename) {
		Criteria criteria = Criteria.where("client").is(client);

		Optional.ofNullable(filepath).ifPresent(x -> criteria.and("folderPath").regex(x));
		Optional.ofNullable(filename).ifPresent(x -> criteria.and("filename").regex(x));
		Query query = Query.query(criteria);
		long total = mongoTemplate.count(query, FileMongo.class);
		query.with(pageable);
		List<File> files = mongoTemplate.find(query, FileMongo.class).stream().flatMap(x -> optionalFile(x).stream()).collect(Collectors.toList());
		return new Page<>(pageable, files, total);
	}


	private Optional<File> optionalFile(FileMongo file) {
		return Optional.ofNullable(file)
			.map(x ->
				File.builder()
					.id(x.get_id())
					.folderPath(x.getFolderPath())
					.filename(x.getFilename())
					.contentType(x.getMetadata().get_contentType())
					.length(x.getLength())
					.md5(x.getMd5())
					.chuckSize(x.getChunkSize())
					.metadata(Metadata.builder()
						.created(
							Metadata.Action.builder()
								.user(userClient.findById(x.getMetadata().getCreated().getUid()))
								.at(x.getMetadata().getCreated().getAt())
								.build()
						).lastModified(
							Metadata.Action.builder()
								.user(userClient.findById(x.getMetadata().getLastModified().getUid()))
								.at(x.getMetadata().getLastModified().getAt())
								.build()
						)
						.build()
					)
					.build()
			);

	}

}
