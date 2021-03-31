package com.hfhk.system.modules.region;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Region {
	private String id;
	private String name;
}
