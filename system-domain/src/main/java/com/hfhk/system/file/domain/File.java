package com.hfhk.system.file.domain;

import com.hfhk.auth.domain.Metadata;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Optional;

@Data
@Accessors(chain = true)

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class File {

	/**
	 * id
	 */
	private String id;

	/**
	 * 文件夹路径
	 */
	private String folderPath;

	/**
	 * filename
	 */
	private String filename;

	/**
	 * contentType
	 */
	private String contentType;

	/**
	 * length
	 */
	private Long length;

	/**
	 * chuckSize
	 */
	private Integer chuckSize;

	/**
	 * md5
	 */
	private String md5;

	/**
	 * 元数据
	 */
	private Metadata metadata;

	/**
	 * 文件后缀
	 *
	 * @return x
	 */
	public String getExtension() {
		return Optional.ofNullable(filename)
			.filter(m -> m.contains("."))
			.map(x -> x.substring(x.lastIndexOf(".") + 1))
			.orElse("");
	}

}
