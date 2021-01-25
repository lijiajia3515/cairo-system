package com.hfhk.system.modules.dictionary;

import com.hfhk.system.modules.dictionary.Dictionary;
import com.hfhk.system.modules.dictionary.DictionaryFindParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "service-system-v1", path = "/Dictionary", contextId = "serviceSystemV1DictionaryClientCredentialsClient")
public interface DictionaryClientCredentialsClient {

	@PostMapping("/Find")
	List<Dictionary> find(@RequestBody DictionaryFindParam param);
}
