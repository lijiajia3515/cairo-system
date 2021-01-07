package com.hfhk.system.file.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FolderSaveParam implements Serializable {
	@Builder.Default
	private String parent = "/";
	private String path;
}
