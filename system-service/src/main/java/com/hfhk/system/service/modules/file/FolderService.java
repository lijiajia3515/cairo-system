package com.hfhk.system.service.modules.file;

import com.hfhk.auth.client.UserClientCredentialsClient;
import com.hfhk.auth.modules.user.User;
import com.hfhk.cairo.core.page.Page;
import com.hfhk.system.file.*;
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
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 文件夹
 */
@Slf4j
@Service
public class FolderService {
	private final HfhkMongoProperties mongoProperties;
	private final MongoTemplate mongoTemplate;
	private final UserClientCredentialsClient userClient;

	public FolderService(HfhkMongoProperties mongoProperties, MongoTemplate mongoTemplate, UserClientCredentialsClient userClient) {
		this.mongoProperties = mongoProperties;
		this.mongoTemplate = mongoTemplate;
		this.userClient = userClient;
	}

	/**
	 * 创建
	 *
	 * @param client client
	 * @param param  param
	 * @return new folder
	 */
	Optional<Folder> save(@NotNull String client, @Validated FolderSaveParam param) {
		Collection<String> paths = FolderUtil.paths(param.getPath());
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
			Collection<FolderMongo> folders = paths.stream().map(path -> FolderConverter.mongoMapper(client, path)).collect(Collectors.toSet());
			folders = mongoTemplate.insert(folders, mongoProperties.COLLECTION.FOLDER);
			log.debug("[folder][create] result -> {}", folders);
			return folders.stream()
				.sorted(Comparator.comparing(FolderMongo::getPath).reversed())
				.limit(1)
				.flatMap(x -> buildFolders(Collections.singletonList(x)).stream())
				.findFirst();
		}
		return Optional.empty(); // throw ex
	}


	/**
	 * 修改
	 *
	 * @param client client
	 * @param param  param
	 * @return modify rename
	 */
	Optional<Folder> rename(@NotNull String client, @Validated FolderRenameParam param) {
		Criteria pathCriteria = Criteria.where(FolderMongo.FIELD.CLIENT).is(client).and(FolderMongo.FIELD.PATH).is(param.getPath());
		Query pathQuery = Query.query(pathCriteria);

		boolean pathExists = mongoTemplate.exists(pathQuery, FolderMongo.class, mongoProperties.COLLECTION.FOLDER);
		if (pathExists) {
			DeleteResult pathDeleteResult = mongoTemplate.remove(pathQuery, FolderMongo.class, mongoProperties.COLLECTION.FOLDER);
			log.debug("[folder][rename]-[pathDelete]->{}", pathDeleteResult);
			return save(client, FolderSaveParam.builder().path(param.getNewPath()).build());
		}
		return Optional.empty();
	}

	/**
	 * 文件夹删除
	 *
	 * @param client client
	 * @param param  param
	 * @return deleted folders
	 */
	List<Folder> delete(@Validated String client, @Validated FolderDeleteParam param) {
		Criteria criteria = Criteria
			.where(FolderMongo.FIELD.CLIENT).is(client)
			.and(FolderMongo.FIELD.PATH).in(param.getPaths());
		Query query = Query.query(criteria);

		List<FolderMongo> deleteFolders = mongoTemplate.findAllAndRemove(query, FolderMongo.class, mongoProperties.COLLECTION.FOLDER);
		log.debug("[folder][delete]-> {}", deleteFolders);
		return buildFolders(deleteFolders);
	}

	/**
	 * 查询
	 *
	 * @param client client
	 * @param param  param
	 * @return filepath
	 */
	List<Folder> find(@NotNull String client, @Validated FolderFindParam param) {
		Criteria criteria = buildFolderCriteria(client, param);
		Query query = Query.query(criteria);

		List<FolderMongo> contents = mongoTemplate.find(query, FolderMongo.class, mongoProperties.COLLECTION.FOLDER);
		return buildFolders(contents);
	}

	/**
	 * 查询
	 *
	 * @param client client
	 * @param param  param
	 * @return filepath
	 */
	Page<Folder> findPage(@NotNull String client, @Validated FolderFindParam param) {
		Criteria criteria = buildFolderCriteria(client, param);
		Query query = Query.query(criteria);
		long total = mongoTemplate.count(query, FolderMongo.class, mongoProperties.COLLECTION.FOLDER);

		query.with(param.pageable());
		List<FolderMongo> contents = mongoTemplate.find(query, FolderMongo.class, mongoProperties.COLLECTION.FOLDER);
		List<Folder> folders = buildFolders(contents);

		return new Page<>(param, folders, total);
	}

	/**
	 * find
	 *
	 * @param client client
	 * @param param  param
	 * @return folder list
	 */
	List<Folder> findTree(@NotNull String client, @Validated FolderFindParam param) {
		Criteria criteria = buildFolderCriteria(client, param);
		Query query = Query.query(criteria);
		List<FolderMongo> contents = mongoTemplate.find(query, FolderMongo.class, mongoProperties.COLLECTION.FOLDER);
		List<Folder> folders = buildFolders(contents);

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

	List<Folder> buildFolders(List<FolderMongo> contents) {
		Set<String> uids = contents.stream().flatMap(x -> Stream.of(x.getMetadata().getCreated().getUid(), x.getMetadata().getLastModified().getUid()))
			.filter(Objects::nonNull)
			.collect(Collectors.toSet());
		Map<String, User> userMap = userClient.findMap(uids);

		return contents
			.stream()
			.map(x -> FolderConverter.folderMapper(x, userMap.get(x.getMetadata().getCreated().getUid()), userMap.get(x.getMetadata().getLastModified().getUid())))
			.collect(Collectors.toList());
	}

}
