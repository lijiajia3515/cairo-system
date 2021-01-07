package com.hfhk.system.service.modules.file;

import com.hfhk.auth.domain.Metadata;
import com.hfhk.auth.domain.user.User;
import com.hfhk.cairo.core.tree.TreeConverter;
import com.hfhk.system.file.domain.Folder;
import com.hfhk.system.service.domain.mongo.FolderMongo;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class FolderConverter {
	public static final String FOLDER_ROOT = "/";

	public static FolderMongo mongoMapper(String client, String path) {
		return FolderMongo.builder().client(client).path(path).build();
	}

	public static Folder folderMapper(String path) {
		String parentPath = FolderUtil.parentPath(path);
		return Folder.builder()
			.id(path)
			.parent(parentPath)
			.build();
	}

	public static Folder folderMapper(FolderMongo folder, User createdUser, User lastModifiedUser) {
		String parentPath = FolderUtil.parentPath(folder.getPath());
		return Folder.builder()
			.id(folder.getPath())
			.parent(parentPath)
			.metadata(Metadata.builder()
				.created(
					Metadata.Action.builder()
						.at(folder.getMetadata().getCreated().getAt())
						.user(createdUser)
						.build())
				.lastModified(
					Metadata.Action.builder()
						.at(folder.getMetadata().getLastModified().getAt())
						.user(lastModifiedUser)
						.build())
				.build()
			)
			.build();
	}

	public static Optional<Folder> folderOptional(String path) {
		return Optional.ofNullable(path).map(FolderConverter::folderMapper);
	}

	public static List<Folder> treeFolderMapper(Collection<Folder> folders) {
		return treeFolderMapper(folders, FOLDER_ROOT);
	}

	public static List<Folder> treeFolderMapper(Collection<Folder> folders, String ROOT) {
		return TreeConverter.build(folders, ROOT, Comparator.comparing(Folder::getId));
	}
}
