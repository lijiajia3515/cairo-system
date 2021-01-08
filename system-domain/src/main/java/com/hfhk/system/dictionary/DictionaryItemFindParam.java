package com.hfhk.system.dictionary;

import com.hfhk.cairo.core.page.AbstractPage;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Set;

/**
 * dictionary save request
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DictionaryItemFindParam extends AbstractPage<DictionaryItemFindParam> implements Serializable {

	private Set<Dictionary> items;

	@Data
	@Accessors(chain = true)
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Dictionary {
		private String id;
		private Set<String> ids;
		private Set<Object> values;
	}
}
