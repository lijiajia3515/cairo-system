package com.hfhk.system.modules.area;

import com.hfhk.cairo.core.tree.TreeNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AreaTree implements TreeNode<String, AreaTree>, Serializable {
	private String id;
	private String parent;
	private String name;

	@Builder.Default
	private List<AreaTree> subs = new ArrayList<>();

	@Override
	public String id() {
		return id;
	}

	@Override
	public String parent() {
		return parent;
	}

	@Override
	public List<AreaTree> subs() {
		return subs;
	}
}
