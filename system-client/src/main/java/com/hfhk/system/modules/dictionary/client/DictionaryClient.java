package com.hfhk.system.modules.dictionary.client;

import com.hfhk.system.modules.dictionary.Dictionary;
import com.hfhk.system.modules.dictionary.DictionaryFindParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(contextId = "dictionaryClient", name = "${hfhk.service.system:service-system-v1}", path = "/Dictionary")
public interface DictionaryClient {

	@PostMapping("/Find")
	List<Dictionary> find(@RequestBody DictionaryFindParam param);
}
