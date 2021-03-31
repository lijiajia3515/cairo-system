package com.hfhk.system.service.domain.mongo;

import com.hfhk.cairo.mongo.data.mapping.model.AbstractMongoField;
import com.hfhk.cairo.mongo.data.mapping.model.AbstractUpperCamelCaseField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
@Accessors(chain = true)

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AreaMongo {
	@MongoId
	private String _id;
	@Field("id")
	private String id;
	private String parent;
	private Integer level;
	private String name;
	private String traditional;
	private String abbr;
	private String pinyin;
	private String zip;
	private List<String> ids;
	private List<String> names;

	public static final MongoField FIELD = new MongoField();

	public static class MongoField extends AbstractUpperCamelCaseField {
		public final String ID = field("Id");
		public final String PARENT = field("Parent");
		public final String LEVEL = field("Level");
		public final String Name = field("Name");
		public final Array IDS = new Array(this, "Ids");
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
