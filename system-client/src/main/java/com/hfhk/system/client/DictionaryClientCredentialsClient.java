package com.hfhk.system.client;

import com.hfhk.system.dictionary.Dictionary;
import com.hfhk.system.dictionary.DictionaryFindParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "service-system-v1", path = "/Dictionary", contextId = "serviceSystemV1DictionaryClientCredentialsClient")
public interface DictionaryClientCredentialsClient {

	@PostMapping("/Find")
	List<Dictionary> find(@RequestBody DictionaryFindParam param);
}
