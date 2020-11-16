package com.hfhk.cairo.system.service.modules.dictionary.domain.request;

import com.hfhk.cairo.system.dictionary.domain.Dictionary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 字典 刷新 值
 */
@Data
@Accessors(chain = true)

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DictionaryItemModifyRequest {

	/**
	 * code
	 */
	private String code;

	/**
	 * 项
	 */
	private Dictionary.Item item;

}
