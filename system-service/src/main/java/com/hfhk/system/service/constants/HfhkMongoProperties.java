package com.hfhk.system.service.constants;

import org.springframework.stereotype.Component;

/**
 * MongoConfig
 */

@Component("hfhkMongoProperties")
public class HfhkMongoProperties {
	public final Collection COLLECTION;

	public HfhkMongoProperties(org.springframework.boot.autoconfigure.mongo.MongoProperties properties) {
		this.COLLECTION = new Collection(properties);
	}

	/**
	 * collection
	 */
	public static class Collection {
		private String Prefix = "system";
		private String Bucket = "";

		public Collection(org.springframework.boot.autoconfigure.mongo.MongoProperties properties) {
			Bucket = properties.getGridfs().getBucket();
		}

		public Collection(org.springframework.boot.autoconfigure.mongo.MongoProperties properties, String prefix) {
			Bucket = properties.getGridfs().getBucket();
			this.Prefix = prefix;
		}

		public final String Dictionary = collection("dictionaries");
		public final String FOLDER = collection("folders");
		public final String FILE = Bucket.concat(".files");

		private String collection(String collection) {
			return Prefix.concat("_").concat(collection);
		}
	}
}
