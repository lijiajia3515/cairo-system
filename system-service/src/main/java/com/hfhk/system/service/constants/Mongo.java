package com.hfhk.system.service.constants;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.stereotype.Component;

/**
 * MongoConfig
 */

@Component
public class Mongo {
	public final Collection Collection;

	public Mongo(MongoProperties properties) {
		this.Collection = new Collection(properties);
	}

	/**
	 * collection
	 */
	public static class Collection {
		private String Prefix = "system";
		private String bucket = "";

		public Collection(MongoProperties properties) {
			bucket = properties.getGridfs().getBucket();
		}

		public Collection(MongoProperties properties, String prefix) {
			bucket = properties.getGridfs().getBucket();
			this.Prefix = prefix;
		}

		public final String Dictionary = collection("dictionaries");
		public final String Folder = collection("folders");
		public final String File = bucket.concat(".files");

		private String collection(String collection) {
			return Prefix.concat("_").concat(collection);
		}
	}
}
