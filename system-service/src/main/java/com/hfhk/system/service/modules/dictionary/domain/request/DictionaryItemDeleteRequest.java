package com.hfhk.system.service.modules.dictionary.domain.request;

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
public class DictionaryItemDeleteRequest implements Serializable {

	/**
	 * code
	 */
	private String code;

	/**
	 * name
	 */
	private Collection<String> itemCodes;
}
