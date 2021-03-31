package com.hfhk.system.service.modules.area;

import com.hfhk.system.modules.area.Area;
import com.hfhk.system.modules.area.AreaTree;
import com.hfhk.system.service.domain.mongo.AreaMongo;
import com.hfhk.system.service.domain.mongo.HfhkMongoProperties;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AreaService {
	private final MongoTemplate mongoTemplate;
	private final HfhkMongoProperties properties;

	public AreaService(MongoTemplate mongoTemplate, HfhkMongoProperties properties) {
		this.mongoTemplate = mongoTemplate;
		this.properties = properties;
	}

	@Cacheable(cacheNames = "area:subs")
	public List<AreaTree> subs(String id) {
		return mongoTemplate.find(Query.query(Criteria.where(AreaMongo.FIELD.PARENT).is(id)), AreaMongo.class, properties.COLLECTION.AREA)
			.stream()
			.map(AreaConverter::areaTree)
			.collect(Collectors.toList());
	}

	@Cacheable(cacheNames = "area:id")
	public Optional<Area> findById(String id) {
		return Optional.ofNullable(mongoTemplate.findOne(Query.query(Criteria.where(AreaMongo.FIELD.ID).is(id)), AreaMongo.class, properties.COLLECTION.AREA))
			.map(AreaConverter::area);
	}
}
