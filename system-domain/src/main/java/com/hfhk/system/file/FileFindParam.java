package com.hfhk.system.file;

import com.hfhk.cairo.core.page.AbstractPage;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileFindParam extends AbstractPage<FileFindParam> implements Serializable {
	private Set<String> ids;
	private Set<Item> items;

	@Data
	@Accessors(chain = true)
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Item {
		String path;
		private String filename;
	}
}
