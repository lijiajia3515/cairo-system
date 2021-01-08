package com.hfhk.system.file;

import com.hfhk.auth.domain.Metadata;
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
public class Folder implements TreeNode<String, Folder>, Serializable {
	/**
	 * id
	 */
	private String id;

	/**
	 * parent
	 */
	private String parent;

	private Metadata metadata;

	/**
	 * 子集
	 */
	@Builder.Default
	private List<Folder> subs = new ArrayList<>(1);

	@Override
	public String id() {
		return id;
	}

	@Override
	public String parent() {
		return parent;
	}

	@Override
	public List<Folder> subs() {
		return subs;
	}
}
