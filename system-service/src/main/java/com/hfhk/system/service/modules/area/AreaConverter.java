package com.hfhk.system.service.modules.area;

import com.hfhk.system.modules.area.Area;
import com.hfhk.system.modules.area.AreaTree;
import com.hfhk.system.service.domain.mongo.AreaMongo;

public class AreaConverter {
	public static AreaTree areaTree(AreaMongo source) {
		return AreaTree.builder().id(source.getId()).parent(source.getParent()).name(source.getName()).build();
	}

	public static Area area(AreaMongo source) {
		return Area.builder().id(source.getId()).parent(source.getParent()).name(source.getName()).ids(source.getIds()).names(source.getNames()).build();
	}
}
