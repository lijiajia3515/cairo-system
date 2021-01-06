package com.hfhk.system.service.modules.test;

import com.hfhk.auth.client.AuthBasicClient;
import com.hfhk.cairo.security.authentication.RemoteUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestApi {
	private final AuthBasicClient client;


	public TestApi(AuthBasicClient client) {
		this.client = client;
	}

	@RequestMapping
	@PermitAll
	public RemoteUser test() {
		return client.auth("");
	}


	@GetMapping("/c")
	@PermitAll
	public Map<String, Object> c(@RequestBody Map<String, Object> c) {
		return c;
	}
}
