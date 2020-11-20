package com.hfhk.cairo.system.service.modules.file.domain.mongo;

import com.hfhk.cairo.system.service.modules.file.constants.MongoConstants;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(MongoConstants.FILE)
@Data
@Accessors(chain = true)

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileMongo {

	/**
	 * id
	 */
	private String _id;

	/**
	 * client
	 */
	private String client;

	/**
	 * folder path
	 */
	private String folderPath;

	/**
	 * filename
	 */
	private String filename;

	/**
	 * 文件大小
	 */
	private Long length;

	/**
	 * chunk Size
	 */
	private Integer chunkSize;

	/**
	 * 文件时间
	 */
	private LocalDateTime uploadDate;

	/**
	 * md5
	 */
	private String md5;

	/**
	 * metadata
	 */
	private Metadata metadata;


	@Data
	@EqualsAndHashCode(callSuper = true)
	@ToString(callSuper = true)

	@NoArgsConstructor
	public static class Metadata extends com.hfhk.cairo.data.mongo.Metadata {
		/**
		 * 文件类型
		 */
		private String _contentType;
	}

}
