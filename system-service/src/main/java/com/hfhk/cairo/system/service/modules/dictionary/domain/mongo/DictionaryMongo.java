package com.hfhk.cairo.system.service.modules.dictionary.domain.mongo;

import com.hfhk.cairo.data.mongo.Metadata;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * 字典
 */
@Document(collection = Mongo.DICTIONARY)
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
}
