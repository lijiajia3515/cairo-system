package com.hfhk.system.service.modules.dictionary.domain.request;

import com.hfhk.system.dictionary.domain.Dictionary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * dictionary save request
 */
@Data
@Accessors(chain = true)

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DictionarySaveParam implements Serializable {

	/**
	 * code
	 */
	private String id;

	/**
	 * name
	 */
	private String name;

	/**
	 * values
	 */
	private List<Dictionary.Item> items;
}
