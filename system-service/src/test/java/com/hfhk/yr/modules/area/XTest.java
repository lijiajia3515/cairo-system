package com.hfhk.yr.modules.area;

import com.hfhk.system.service.SystemServiceApp;
import com.hfhk.system.service.domain.mongo.Area2Mongo;
import com.hfhk.system.service.domain.mongo.AreaMongo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootTest(classes = SystemServiceApp.class)
public class XTest {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Test
	public void test() {
		List<Area2Mongo> a0 = mongoTemplate.find(Query.query(Criteria.where("Parent").is("0")), Area2Mongo.class, "hfhk_area2");
		a0.forEach(a0i -> {
			AreaMongo b0i = mongoTemplate.insert(AreaMongo.builder().parent("").id(a0i.getCode()).name(a0i.getName()).parent("").ids(Collections.singletonList(a0i.getCode())).names(Collections.singletonList(a0i.getName())).level(1).build(), "system_area");
			List<Area2Mongo> a1 = mongoTemplate.find(Query.query(Criteria.where("Parent").is(a0i.getId())), Area2Mongo.class, "hfhk_area2");
			a1.forEach(a1i -> {
				AreaMongo b1i = mongoTemplate.insert(AreaMongo.builder().parent(b0i.get_id()).id(a1i.getCode()).name(a1i.getName()).parent(a0i.getCode()).ids(Arrays.asList(a0i.getCode(), a1i.getCode())).names(Arrays.asList(a0i.getName(), a1i.getName())).level(2).build(), "system_area");
				List<Area2Mongo> a2 = mongoTemplate.find(Query.query(Criteria.where("Parent").is(a1i.getId())), Area2Mongo.class, "hfhk_area2");
				a2.forEach(a2i -> {
					AreaMongo b2i = mongoTemplate.insert(AreaMongo.builder().parent(b1i.get_id()).id(a2i.getCode()).name(a2i.getName()).parent(a1i.getCode()).ids(Arrays.asList(a0i.getCode(), a1i.getCode(), a2i.getCode())).names(Arrays.asList(a0i.getName(), a1i.getName(), a2i.getName())).level(3).build(), "system_area");
					List<Area2Mongo> a3 = mongoTemplate.find(Query.query(Criteria.where("Parent").is(a2i.getId())), Area2Mongo.class, "hfhk_area2");
					a3.forEach(a3i -> {
						AreaMongo b3i = mongoTemplate.insert(AreaMongo.builder().parent(b2i.get_id()).id(a3i.getCode()).name(a3i.getName()).parent(a2i.getCode()).ids(Arrays.asList(a0i.getCode(), a1i.getCode(), a2i.getCode(), a3i.getCode())).names(Arrays.asList(a0i.getName(), a1i.getName(), a2i.getName(), a3i.getName())).level(4).build(), "system_area");
						List<Area2Mongo> a4 = mongoTemplate.find(Query.query(Criteria.where("Parent").is(a3i.getId())), Area2Mongo.class, "hfhk_area2");
						a4.forEach(a4i -> {
							AreaMongo b4i = mongoTemplate.insert(AreaMongo.builder().parent(b3i.get_id()).id(a4i.getCode()).name(a4i.getName()).parent(a3i.getCode()).ids(Arrays.asList(a0i.getCode(), a1i.getCode(), a2i.getCode(), a3i.getCode(), a4i.getCode())).names(Arrays.asList(a0i.getName(), a1i.getName(), a2i.getName(), a3i.getName(), a4i.getName())).level(2).build(), "system_area");
						});
					});
				});
			});
		});

	}
}
