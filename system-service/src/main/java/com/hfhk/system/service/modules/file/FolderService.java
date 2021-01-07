package com.hfhk.system.service.modules.file;

import com.hfhk.cairo.core.page.Page;
import com.hfhk.system.file.domain.Folder;
import com.hfhk.system.file.domain.FolderDeleteParam;
import com.hfhk.system.file.domain.FolderFindParam;
import com.hfhk.system.service.constants.HfhkMongoProperties;
import com.hfhk.system.service.domain.mongo.FolderMongo;
import com.mongodb.client.result.DeleteResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 文件夹
 */
@Slf4j
@Service
public class FolderService {
	private final HfhkMongoProperties mongoProperties;
	private final MongoTemplate mongoTemplate;

	public FolderService(HfhkMongoProperties mongoProperties, MongoTemplate mongoTemplate) {
		this.mongoProperties = mongoProperties;
		this.mongoTemplate = mongoTemplate;
	}

	/**
	 * 创建
	 *
	 * @param client client
	 * @param path   path
	 */
	void save(String client, String path) {
		Collection<String> paths = FolderUtil.paths(path);
		if (!paths.isEmpty()) {
			Criteria criteria = Criteria
				.where(FolderMongo.FIELD.CLIENT).is(client)
				.and(FolderMongo.FIELD.PATH).in(paths);
			Query query = Query.query(criteria);
			query.fields().include(FolderMongo.FIELD.PATH);

			Set<String> existsPaths = mongoTemplate.find(query, FolderMongo.class, mongoProperties.COLLECTION.FOLDER)
				.stream()
				.map(FolderMongo::getPath)
				.collect(Collectors.toSet());

			paths.removeAll(existsPaths);
			Collection<FolderMongo> folders = paths.stream().map(x -> FolderConverter.mongoMapper(client, path)).collect(Collectors.toSet());
			folders = mongoTemplate.insertAll(folders);
			log.debug("[folder][create] result -> {}", folders);
		}
	}


	/**
	 * 修改
	 *
	 * @param client  client
	 * @param path    path
	 * @param newPath new file path
	 */
	void rename(String client, String path, String newPath) {
		Criteria pathCriteria = Criteria.where(FolderMongo.FIELD.CLIENT).is(client).and(FolderMongo.FIELD.CLIENT).is(path);
		Query pathQuery = Query.query(pathCriteria);

		boolean pathExists = mongoTemplate.exists(pathQuery, FolderMongo.class, mongoProperties.COLLECTION.FOLDER);
		if (pathExists) {
			DeleteResult pathDeleteResult = mongoTemplate.remove(pathQuery, FolderMongo.class, mongoProperties.COLLECTION.FOLDER);
			log.debug("[folder][rename]-[pathDelete]->{}", pathDeleteResult);
			save(client, newPath);
		}

	}

	/**
	 * 文件夹删除
	 *
	 * @param client client
	 * @param param  param
	 */
	void delete(@Validated String client, @Validated FolderDeleteParam param) {
		Criteria criteria = Criteria
			.where(FolderMongo.FIELD.CLIENT).is(client)
			.and(FolderMongo.FIELD.PATH).in(param.getPaths());
		Query query = Query.query(criteria);

		List<FolderMongo> deleteFolders = mongoTemplate.findAllAndRemove(query, FolderMongo.class, mongoProperties.COLLECTION.FOLDER);
		log.debug("[folder][delete]-> {}", deleteFolders);
	}


	/**
	 * 查询
	 *
	 * @param client client
	 * @param param  param
	 * @return filepath
	 */
	Page<String> findPage(@NotNull String client, @Validated FolderFindParam param) {
		Criteria criteria = buildFolderCriteria(client, param);
		Query query = Query.query(criteria);
		query.fields().include(FolderMongo.FIELD.PATH);
		long total = mongoTemplate.count(query, FolderMongo.class);

		query.with(param.pageable());
		List<String> paths = mongoTemplate.find(query, FolderMongo.class).stream()
			.map(FolderMongo::getPath).sorted().collect(Collectors.toList());

		return new Page<>(param, paths, total);

	}

	/**
	 * 查询
	 *
	 * @param client client
	 * @param param  param
	 * @return x
	 */
	List<Folder> findTree(@NotNull String client, @Validated FolderFindParam param) {
		Criteria criteria = buildFolderCriteria(client, param);
		Query query = Query.query(criteria);
		Set<Folder> folders = mongoTemplate.find(query, FolderMongo.class)
			.stream()
			.map(FolderMongo::getPath)
			.flatMap(x -> FolderUtil.paths(x).stream())
			.flatMap(x -> FolderConverter.folderOptional(x).stream())
			.collect(Collectors.toSet());

		return FolderConverter.treeFolderMapper(folders);
	}

	Criteria buildFolderCriteria(@NotNull String client, @Validated FolderFindParam param) {
		Criteria criteria = Criteria.where(FolderMongo.FIELD.CLIENT).is(client);
		Optional.ofNullable(param.getPaths()).filter(x -> !x.isEmpty())
			.ifPresent(paths -> {
				Criteria[] pathCriteria = paths.stream()
					.map(path -> Criteria.where(FolderMongo.FIELD.PATH).regex(path))
					.distinct()
					.toArray(Criteria[]::new);
				criteria.orOperator(pathCriteria);
			});
		return criteria;
	}

}
