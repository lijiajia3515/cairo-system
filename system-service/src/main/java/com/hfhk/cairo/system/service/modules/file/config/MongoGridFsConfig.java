package com.hfhk.cairo.system.service.modules.file.config;

import com.hfhk.cairo.system.service.modules.file.constants.MongoConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

@Configuration
public class MongoGridFsConfig {

	@Bean
	@Primary
	public GridFsTemplate harpoonGridFsTemplate(MongoDbFactory mongoDbFactory, MongoTemplate mongoTemplate) {
		return new GridFsTemplate(mongoDbFactory, mongoTemplate.getConverter(), MongoConstants.BUCKET);
	}
}
