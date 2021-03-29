package com.hfhk.system.service.modules.area;

import com.hfhk.system.modules.area.Area;
import com.hfhk.system.modules.area.AreaTree;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Area")
public class AreaApi {
	private final AreaService areaService;

	public AreaApi(AreaService areaService) {
		this.areaService = areaService;
	}

	@PostMapping("/Find/Id/{id}")
	public Optional<Area> findById(@PathVariable String id) {
		return areaService.findById(id);
	}

	@PostMapping("/Find/Subs/{id}")
	public List<AreaTree> find(@PathVariable String id) {
		return areaService.subs(id);
	}
}
