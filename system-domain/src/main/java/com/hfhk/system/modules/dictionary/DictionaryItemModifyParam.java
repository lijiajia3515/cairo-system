package com.hfhk.system.modules.dictionary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 字典 刷新 值
 */
@Data
@Accessors(chain = true)

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DictionaryItemModifyParam implements Serializable {

	/**
	 * code
	 */
	private String id;

	/**
	 * 项
	 */
	private Dictionary.Item item;

}
