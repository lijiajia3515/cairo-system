package com.hfhk.system.service.domain.mongo;

import com.hfhk.cairo.core.tree.TreeNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.List;

/**
 * 字典
 */
@Data
@Accessors(chain = true)

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Area2Mongo implements TreeNode<String, Area2Mongo> {

	@MongoId
	private String _id;
	/**
	 * id
	 */
	private String id;

	private String parent;

	/**
	 * code 值
	 */
	private String code;

	/**
	 * 名称
	 */
	private String name;

	private String level;

	@Builder.Default
	private List<Area2Mongo> subs = new ArrayList<>();

	@Override
	public String id() {
		return id;
	}

	@Override
	public String parent() {
		return parent;
	}

	@Override
	public List<Area2Mongo> subs() {
		return subs;
	}
}
