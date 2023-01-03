package com.mulcam.demo.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mulcam.demo.entity.StaticMap;

@Controller
@RequestMapping("/map")
public class MapController {
	
	@Value("${naver.accessId}")
	private String accessId;
	
	@Value("${naver.secretKey}")
	private String secretKey;
	
	@GetMapping("/staticMap")
	public String staticForm() {
		return "map/staticForm";
	}
	
	@PostMapping("/staticMap")
	public String staticMap(StaticMap map, Model model) throws UnsupportedEncodingException {
		String url = "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster"
					+ "?w=" + map.getWidth() 
					+ "&h=" + map.getHeight() 
					+ "&center=" + map.getLng() + "," + map.getLat()
					+ "&level=" + map.getLevel()
					+ "&maptype=" + map.getMaptype()
					+ "&format=" + map.getFormat()
					+ "&scale=" + map.getScale()
					+ "&lang=" + map.getLang()
					+ "&X-NCP-APIGW-API-KEY-ID=" + accessId
					+ "&X-NCP-APIGW-API-KEY=" + secretKey;
		
		String marker = "type:d|size:mid|pos:127.0724 37.5383";
		marker = URLEncoder.encode(marker, "utf-8");
		url += "&markers=" + marker;
		
		marker = "type:t|size:tiny|pos:127.0824 37.5383|label:광진구청|color:red";
		marker = URLEncoder.encode(marker, "utf-8");
		url += "&markers=" + marker;
		
		model.addAttribute("url", url);
		return "map/staticResult";
	}
}

