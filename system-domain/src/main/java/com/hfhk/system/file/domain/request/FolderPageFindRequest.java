package com.hfhk.system.file.domain.request;

import com.hfhk.cairo.core.request.PageRequest;
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
public class FolderPageFindRequest implements Serializable {
	@Builder.Default
	private PageRequest page = new PageRequest();

	private String path;
}
