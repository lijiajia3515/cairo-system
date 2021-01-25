package com.hfhk.system.modules.dictionary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Collection;

/**
 * dictionary save request
 */
@Data
@Accessors(chain = true)

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DictionaryItemDeleteParam implements Serializable {

	/**
	 * code
	 */
	private String id;

	/**
	 * name
	 */
	private Collection<String> itemIds;
}
