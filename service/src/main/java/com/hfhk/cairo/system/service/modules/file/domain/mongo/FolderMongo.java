package com.hfhk.cairo.system.service.modules.file.domain.mongo;

import com.hfhk.cairo.data.mongo.Metadata;
import com.hfhk.cairo.system.service.modules.file.constants.MongoConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 文件夹
 */
@Document(MongoConstants.FOLDER)

@Data
@Accessors(chain = true)

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FolderMongo {
	/**
	 * id
	 */
	private String id;

	/**
	 * client
	 */
	private String client;

	/**
	 * path
	 */
	private String path;

	/**
	 * 元数据
	 */
	@Builder.Default
	private Metadata metadata = new Metadata();
}
