package com.hfhk.system.modules.area;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class GDArea {
	 private List<String> citycode;
	 private String adcode;
	 private String name;
	 private String center;
	 private String level;
	 private List<GBAreaProvince> districts;

	@Data
	@Accessors(chain = true)
	@NoArgsConstructor
	@AllArgsConstructor
	 public static class GBAreaProvince{
		 private List<String> citycode;
		 private String adcode;
		 private String name;
		 private String center;
		 private String level;
		 private List<Object> districts;
	 }

	@Data
	@Accessors(chain = true)
	@NoArgsConstructor
	@AllArgsConstructor
	public static class GBAreaCity{
		private String citycode;
		private String adcode;
		private String name;
		private String center;
		private String level;
		private List<Object> districts;
	}
}
