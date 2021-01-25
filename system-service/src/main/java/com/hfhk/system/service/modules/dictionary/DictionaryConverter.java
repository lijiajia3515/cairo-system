package com.hfhk.system.service.modules.dictionary;

import com.hfhk.system.modules.dictionary.Dictionary;
import com.hfhk.system.service.domain.mongo.DictionaryMongo;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class DictionaryConverter {
	public static Dictionary mapper(DictionaryMongo mongo) {
		return Dictionary.builder()
			.id(mongo.getCode())
			.name(mongo.getName())
			.items(
				Optional
					.ofNullable(mongo.getItems())
					.stream().flatMap(Collection::stream)
					.map(y -> Dictionary.Item.builder().id(y.getId()).value(y.getValue()).name(y.getName()).build())
					.collect(Collectors.toList())
			)
			.build();
	}

	/**
	 * mongo to model
	 *
	 * @param mongo mongo
	 * @return dictionary optional
	 */
	private static Optional<Dictionary> mapperOptional(DictionaryMongo mongo) {
		return Optional.ofNullable(mongo)
			.map(DictionaryConverter::mapper);
	}
}
