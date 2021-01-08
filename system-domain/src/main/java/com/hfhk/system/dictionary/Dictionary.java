package com.hfhk.system.dictionary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 字典
 */
@Data
@Accessors(chain = true)

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dictionary implements Serializable {

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
	private List<Item> items;

	@Data
	@Accessors(chain = true)
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Item implements Serializable {
		/**
		 * id
		 */
		private String id;

		/**
		 * value
		 */
		private Object value;

		/**
		 * name
		 */
		private String name;
	}
}
