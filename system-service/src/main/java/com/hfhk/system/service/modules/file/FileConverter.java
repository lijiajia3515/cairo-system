package com.hfhk.system.service.modules.file;

import com.hfhk.auth.domain.Metadata;
import com.hfhk.auth.domain.user.User;
import com.hfhk.system.file.domain.File;
import com.hfhk.system.service.domain.mongo.FileMongo;

import java.util.Optional;

public class FileConverter {
	public static File fileMapper(FileMongo mongo, User createdUser, User lastModifiedUser) {
		return File.builder()
			.id(mongo.get_id())
			.path(mongo.getPath())
			.filename(mongo.getFilename())
			.contentType(mongo.getMetadata().get_contentType())
			.length(mongo.getLength())
			.md5(mongo.getMd5())
			.chuckSize(mongo.getChunkSize())
			.metadata(Metadata.builder()
				.created(
					Metadata.Action.builder()
						.user(createdUser)
						.at(mongo.getMetadata().getCreated().getAt())
						.build()
				).lastModified(
					Metadata.Action.builder()
						.user(lastModifiedUser)
						.at(mongo.getMetadata().getLastModified().getAt())
						.build()
				)
				.build()
			)
			.build();
	}

	private static Optional<File> fileOptional(FileMongo mongo, User createdUser, User lastModifiedUser) {
		return Optional.ofNullable(mongo).map(x -> FileConverter.fileMapper(x, createdUser, lastModifiedUser));

	}
}
