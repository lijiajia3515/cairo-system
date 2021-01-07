package com.hfhk.system.service.modules.file;

import com.hfhk.cairo.core.business.Business;
import lombok.Getter;
import lombok.experimental.Accessors;


@Getter
@Accessors(fluent = true)
public enum FileBusiness implements Business {
	/**
	 * 业务默认成功结果
	 */
	UploadField("文件上传失败"),

	;

	public final boolean success;
	public final String code = "File".concat(name());
	public final String message;


	FileBusiness(String message) {
		this.success = true;
		this.message = message;
	}

	FileBusiness(boolean success, String message) {
		this.success = success;
		this.message = message;
	}
}
