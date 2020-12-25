package com.hfhk.system.service.domain.mongo;


import com.hfhk.cairo.mongo.data.mapping.model.UpperCamelCaseFieldNames;
import lombok.*;
import lombok.experimental.Accessors;

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
	 * Metadata
	 */
	@Builder.Default
	private Metadata metadata = new Metadata();

	@Data
	@EqualsAndHashCode(callSuper = true)
	@ToString(callSuper = true)

	@NoArgsConstructor
	public static class Metadata extends com.hfhk.cairo.mongo.data.Metadata {
		/**
		 * 文件类型
		 */
		private String _contentType;
	}

	public static class Field extends UpperCamelCaseFieldNames {
		public static final String Client = "client";
		public static final String FolderPath = "folderPath";
		public static final String Filename = "filename";
		public static final String Length = "length";
		public static final String ChuckSize = "chuckSize";
		public static final String UploadDate = "uploadDate";
		public static final String md5 = "md5";
		public static final Metadata Metadata = new Metadata();

		public static class Metadata extends UpperCamelCaseFieldNames.Metadata {
			public static final String _ContentType = "_contentType";
		}
	}

}
