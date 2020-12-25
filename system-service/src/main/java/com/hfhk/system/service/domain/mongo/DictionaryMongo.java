package com.hfhk.system.service.domain.mongo;

import com.hfhk.cairo.mongo.data.Metadata;
import com.hfhk.cairo.mongo.data.mapping.model.UpperCamelCaseFieldNames;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 字典
 */

@Data
@Accessors(chain = true)

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DictionaryMongo {

	/**
	 * id
	 */
	private String id;

	/**
	 * code 值
	 */
	private String code;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * value值
	 */
	private List<Item> items;

	@Builder.Default
	private Metadata metadata = new Metadata();

	/**
	 * Dictionary Item
	 */
	@Data
	@Accessors(chain = true)

	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Item {
		/**
		 * value code
		 */
		private String code;
		/**
		 * value
		 */
		private Object value;
		/**
		 * metadata
		 */
		@Builder.Default
		private Metadata metadata = new Metadata();
	}

	public static class Field extends UpperCamelCaseFieldNames {
		public static final String Client = "Client";
		public static final String Code = "Code";
		public static final String Name = "Name";
		public static final String Items = "Items";

		public static class Item extends UpperCamelCaseFieldNames {
			public static final String Prefix = "Items";
			public static final String Code = "Items.Code";
			public static final String Name = "Items.Name";
		}
	}
}
