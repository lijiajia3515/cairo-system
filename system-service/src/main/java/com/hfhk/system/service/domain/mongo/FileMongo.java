package com.hfhk.system.service.domain.mongo;


import com.hfhk.cairo.mongo.data.mapping.model.AbstractMongoField;
import com.hfhk.cairo.mongo.data.mapping.model.AbstractUpperCamelCaseField;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileMongo {

	/**
	 * id
	 */
	@MongoId
	private String _id;

	/**
	 * client
	 */
	private String client;

	/**
	 * folder path
	 */
	private String path;

	/**
	 * filename
	 */
	@Field("filename")
	private String filename;

	/**
	 * 文件大小
	 */
	@Field("length")
	private Long length;

	/**
	 * chunk Size
	 */
	@Field("chunkSize")
	private Integer chunkSize;

	/**
	 * 文件时间
	 */
	@Field("uploadDate")
	private LocalDateTime uploadDate;

	/**
	 * md5
	 */
	@Field("md5")
	private String md5;

	/**
	 * Metadata
	 */
	@Builder.Default
	@Field("metadata")
	private Metadata metadata = new Metadata();

	public static final MongoField FIELD = new MongoField();

	@Data
	@EqualsAndHashCode(callSuper = true)
	@ToString(callSuper = true)

	@NoArgsConstructor
	public static class Metadata extends com.hfhk.cairo.mongo.data.Metadata {
		/**
		 * 文件类型
		 */
		@org.springframework.data.mongodb.core.mapping.Field("_contentType")
		private String _contentType;
	}

	public static class MongoField extends AbstractUpperCamelCaseField {
		public final String CLIENT = field("client");
		public final String PATH = field("path");
		public final String FILENAME = field("filename");
		public final String LENGTH = field("length");
		public final String CHUCK_SIZE = field("chuckSize");
		public final String UPLOAD_DATE = field("uploadDate");
		public final String MD5 = "md5";
		public final Metadata Metadata = new Metadata(this, "Metadata");

		public static class Metadata extends AbstractUpperCamelCaseField.Metadata {
			public Metadata() {
			}

			public Metadata(AbstractMongoField parent, String prefix) {
				super(parent, prefix);
			}

			public static final String _CONTENT_TYPE = "_contentType";
		}
	}

}
