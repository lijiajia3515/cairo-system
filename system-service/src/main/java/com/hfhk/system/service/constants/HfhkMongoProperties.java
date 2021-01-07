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
		private String PREFIX = "system";
		private final String BUCKET;

		public Collection(org.springframework.boot.autoconfigure.mongo.MongoProperties properties) {
			this.BUCKET = properties.getGridfs().getBucket();
		}

		public Collection(org.springframework.boot.autoconfigure.mongo.MongoProperties properties, String prefix) {
			this.BUCKET = properties.getGridfs().getBucket();
			this.PREFIX = prefix;
		}

		public final String DICTIONARY = collection("dictionaries");
		public final String FOLDER = collection("folders");

		private String collection(String collection) {
			return PREFIX.concat("_").concat(collection);
		}

		public final String file(){
			return BUCKET.concat(".files");
		}
	}
}
