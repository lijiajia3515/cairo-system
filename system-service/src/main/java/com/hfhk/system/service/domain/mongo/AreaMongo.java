package com.hfhk.system.service.domain.mongo;

import com.hfhk.cairo.mongo.data.mapping.model.AbstractMongoField;
import com.hfhk.cairo.mongo.data.mapping.model.AbstractUpperCamelCaseField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AreaMongo {
	private String id;
	private String parent;
	private Integer level;
	private String code;
	private String parentCode;
	private String name;
	private List<String> codes;
	private List<String> names;

	public static final Field FIELD = new Field();

	public static class Field extends AbstractUpperCamelCaseField {
		public final String PARENT = field("Parent");
		public final String LEVEL = field("Level");
		public final String CODE = field("Code");
		public final String PARENT_CODE = field("ParentCode");
		public final String Name = field("Name");
		public final Array CODES = new Array(this, "Codes");
		public final Array NAMES = new Array(this, "Names");
		public final DictionaryMongo.Field.Items ITEMS = new DictionaryMongo.Field.Items(this, "Items");

		public static class Array extends AbstractUpperCamelCaseField {
			public Array() {
			}

			public Array(AbstractMongoField parent, String prefix) {
				super(parent, prefix);
			}

			public final String index(Integer i) {
				return field("" + i);
			}
		}
	}
}
