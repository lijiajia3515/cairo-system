package com.hfhk.system.modules.area;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Area implements Serializable {
	private String id;
	private String parent;
	private String name;
	private Integer level;
	private List<String> ids;
	private List<String> names;
}
