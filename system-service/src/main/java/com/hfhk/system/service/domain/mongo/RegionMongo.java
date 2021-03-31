package com.hfhk.system.service.domain.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Accessors(chain = true)

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegionMongo {
	@MongoId
	private String _id;
	@Field("id")
	private String id;
	@Field("pid")
	private String pid;
	@Field("name")
	private String name;
	@Field("abbr")
	private String abbr;
	@Field("pinyin")
	private String pinyin;
	@Field("tname")
	private String tname;
	@Field("type")
	private Integer type;
	@Field("zip")
	private String zip;
}
