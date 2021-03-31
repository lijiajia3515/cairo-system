package com.hfhk.yr.modules;

import com.hfhk.cairo.mongo.data.MongoConstants;
import com.hfhk.system.service.SystemServiceApp;
import com.hfhk.system.service.domain.mongo.AreaMongo;
import com.hfhk.system.service.domain.mongo.RegionMongo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest(classes = SystemServiceApp.class)
public class RegionTest {
	@Autowired
	private MongoTemplate mongoTemplate;

	@Test
	public void test() {
		List<RegionMongo> regions = mongoTemplate.findAll(RegionMongo.class, "region");
		List<AreaMongo> areas = new ArrayList<>();
		List<RegionMongo> countries = regions.stream().filter(x -> "0".equals(x.getPid())).collect(Collectors.toList());
		countries.stream().sorted(Comparator.comparing(RegionMongo::getId)).forEach(c -> {
			AreaMongo country = AreaMongo.builder()
				.id(c.getId())
				.parent(c.getPid())
				.name(c.getName())
				.traditional(c.getTname())
				.abbr(c.getAbbr())
				.pinyin(c.getPinyin())
				.level(c.getType())
				.zip(c.getZip())
				.ids(Arrays.asList(c.getId()))
				.names(Arrays.asList(c.getName()))
				.build();
			areas.add(country);
			List<RegionMongo> provinces = regions.stream().filter(x -> country.getId().equals(x.getPid())).collect(Collectors.toList());
			provinces.stream().sorted(Comparator.comparing(RegionMongo::getId)).forEach(c1 -> {
				AreaMongo province = AreaMongo.builder()
					.id(c1.getId())
					.parent(c1.getPid())
					.name(c1.getName())
					.traditional(c1.getTname())
					.abbr(c1.getAbbr())
					.pinyin(c1.getPinyin())
					.level(c1.getType())
					.zip(c1.getZip())
					.ids(Arrays.asList(c.getId(), c1.getId()))
					.names(Arrays.asList(c.getId(), c1.getName()))
					.build();
				areas.add(province);
				List<RegionMongo> cities = regions.stream().filter(x -> province.getId().equals(x.getPid())).collect(Collectors.toList());
				cities.stream().sorted(Comparator.comparing(RegionMongo::getId)).forEach(c2 -> {
					AreaMongo city = AreaMongo.builder()
						.id(c2.getId())
						.parent(c2.getPid())
						.name(c2.getName())
						.traditional(c2.getTname())
						.abbr(c2.getAbbr())
						.pinyin(c2.getPinyin())
						.level(c2.getType())
						.zip(c2.getZip())
						.ids(Arrays.asList(c.getId(), c1.getId(), c2.getId()))
						.names(Arrays.asList(c.getId(), c1.getId(), c2.getName()))
						.build();
					areas.add(city);
					List<RegionMongo> districts = regions.stream().filter(x -> city.getId().equals(x.getPid())).collect(Collectors.toList());
					districts.stream().sorted(Comparator.comparing(RegionMongo::getId)).forEach(c3 -> {
						AreaMongo district = AreaMongo.builder()
							.id(c3.getId())
							.parent(c3.getPid())
							.name(c3.getName())
							.traditional(c3.getTname())
							.abbr(c3.getAbbr())
							.pinyin(c3.getPinyin())
							.level(c3.getType())
							.zip(c3.getZip())
							.ids(Arrays.asList(c.getId(), c1.getId(), c2.getId(), c3.getId()))
							.names(Arrays.asList(c.getId(), c1.getId(), c2.getId(), c3.getName()))
							.build();
						areas.add(district);

						List<RegionMongo> streets = regions.stream().filter(x -> district.getId().equals(x.getPid())).collect(Collectors.toList());
						streets.stream().sorted(Comparator.comparing(RegionMongo::getId)).forEach(c4 -> {
							AreaMongo street = AreaMongo.builder()
								.id(c4.getId())
								.parent(c4.getPid())
								.name(c4.getName())
								.traditional(c4.getTname())
								.abbr(c4.getAbbr())
								.pinyin(c4.getPinyin())
								.level(c4.getType())
								.zip(c4.getZip())
								.ids(Arrays.asList(c.getId(), c1.getId(), c2.getId(), c3.getId(), c4.getId()))
								.names(Arrays.asList(c.getId(), c1.getId(), c2.getId(), c3.getId(), c4.getName()))
								.build();
							areas.add(street);
							List<RegionMongo> unknowns = regions.stream().filter(x -> street.getId().equals(x.getPid())).collect(Collectors.toList());
							unknowns.stream().sorted(Comparator.comparing(RegionMongo::getId)).forEach(c5 -> {
								AreaMongo unknown = AreaMongo.builder()
									.id(c5.getId())
									.parent(c5.getPid())
									.name(c5.getName())
									.traditional(c5.getTname())
									.abbr(c5.getAbbr())
									.pinyin(c5.getPinyin())
									.level(c5.getType())
									.zip(c5.getZip())
									.ids(Arrays.asList(c.getId(), c1.getId(), c2.getId(), c3.getId(), c4.getId(), c5.getId()))
									.names(Arrays.asList(c.getId(), c1.getId(), c2.getId(), c3.getId(), c4.getId(), c5.getName()))
									.build();
								areas.add(unknown);
							});
						});
					});
				});
			});
		});
		areas.sort(Comparator.comparing(AreaMongo::getId));
		mongoTemplate.insert(areas, "system_area");
	}

}
