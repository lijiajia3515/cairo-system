package com.hfhk.system.modules.dictionary;

import com.hfhk.cairo.core.page.AbstractPage;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DictionaryFindParam extends AbstractPage<DictionaryFindParam> {
	private String keyword;
	private Set<Dictionary> items;

	@Data
	@Accessors(chain = true)
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Dictionary {
		private String id;
		private Set<String> itemIds;
		private Set<Object> itemValues;
	}
}
