package com.hfhk.system.service.modules.file;

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
