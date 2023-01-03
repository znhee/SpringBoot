package com.mulcam.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/map")
public class MapController {
	
	@Value("${naver.accessId}")
	private String accessId;
	@Value("${naver.secretKey}")
	private String secretKey;
	
	@ResponseBody
	@GetMapping("/staticMap")
	public String staticMap() {
		
		return "accessId: " + accessId + "<br>secretKey: " + secretKey;
	}
}
