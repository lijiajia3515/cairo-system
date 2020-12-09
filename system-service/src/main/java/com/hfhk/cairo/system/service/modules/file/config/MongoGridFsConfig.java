package com.hfhk.cairo.system.service.modules.file.config;

import com.hfhk.cairo.system.service.modules.file.constants.MongoConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

@Configuration
public class MongoGridFsConfig {

	@Bean
	@Primary
	public GridFsTemplate harpoonGridFsTemplate(MongoDatabaseFactory mongoDatabaseFactory, MongoTemplate mongoTemplate) {
		return new GridFsTemplate(mongoDatabaseFactory, mongoTemplate.getConverter(), MongoConstants.BUCKET);
	}
}
