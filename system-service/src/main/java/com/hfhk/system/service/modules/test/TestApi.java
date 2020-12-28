package com.hfhk.system.service.modules.test;

import com.hfhk.auth.client.AuthenticationBasicClient;
import com.hfhk.system.service.client.AClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;

@RestController
@RequestMapping("/test")
public class TestApi {
	private final AuthenticationBasicClient client;

	private final AClient aClient;

	public TestApi(AuthenticationBasicClient client, AClient aClient) {
		this.client = client;
		this.aClient = aClient;
	}

	@RequestMapping
	@PermitAll
	public Object test(){
		return client.authentication("");
	}

	@RequestMapping("/b")
	@PermitAll
	public Object b() {
		return aClient.authentication("eyJraWQiOiJlNWUwYjE4Ny1kMGEyLTRhN2YtYWE2Yi1jZGFhMTRkY2I4MWMiLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJyb290IiwiYXVkIjoiaGZoa19kaGd4anNqIiwibmJmIjoxNjA5MTIyMTA0LCJzY29wZSI6WyJvcGVuaWQiXSwiaXNzIjoiaHR0cDpcL1wvYXV0aC5oZmhrc29mdC5jb20iLCJleHAiOjE2MTQzMDYxMDQsImlhdCI6MTYwOTEyMjEwNCwianRpIjoiZDc0ZGIxZmMtZjMyZS00OGNmLTkxOGItOTUxN2IwZjk0YjQ5In0.fua6eI_LBK1geeEnOJJmK7BNpJfIFq_jla8DU-Bw4cop3VpAIXgpjlQQ7jYmJ8K-Fz6B3cwrIL8elYuNIAQiwErpqzDrLmcu96rFyByIlzMUs28Nc4U9hhN0MNnUIK8LX8OLEaLLfINXxy4yFcfJIoaf_wH_9eFajUFtRCWpnu1Yz--qcJTp6A9EJ_yJDW3xEOtN4pxdtJnwcuq3PlBv2hZ-UUABYapYxE2sqoG_JSMep0oAz4W_iTTvU7a6ILce6Sz55BRHId2rJqILb_Kcn66ka1z8yomNdl1QGzzG9u9H9DUDbQ-Y-GaCuJhjFpoiq3gsABjEfCBG1bvyYL0YLg");
	}
}
