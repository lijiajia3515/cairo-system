package com.hfhk.system.file.domain;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FolderRenameParam implements Serializable {

	private String path;
	private String newPath;
}
