package com.hfhk.system.modules.dictionary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 字典 刷新 值
 */
@Data
@Accessors(chain = true)

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DictionaryItemPutParam implements Serializable {

	/**
	 * code
	 */
	private String id;

	/**
	 * 项
	 */
	private List<Dictionary.Item> items;

}
