package com.mulcam.demo.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/detect")
public class DetectController {
	
	@Value("${naver.accessId}") private String accessId;
	@Value("${naver.secretKey}") private String secretKey;
	
	@ResponseBody
	@GetMapping("/naver")
	public String naver() throws Exception {
		String apiURL = "https://naveropenapi.apigw.ntruss.com/vision-obj/v1/detect"; // 객체 인식
		File uploadFile = new File("/tmp/yolo-test.jpeg");
		
		URL url = new URL(apiURL);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setUseCaches(false);
        conn.setDoOutput(true);
        conn.setDoInput(true);
		conn.setRequestMethod("POST");		// 생략 가능
		
        // multipart request
		String boundary = "---" + System.currentTimeMillis() + "---";
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", accessId);
        conn.setRequestProperty("X-NCP-APIGW-API-KEY", secretKey);
        
        // 파일 전송 준비 
        OutputStream os = conn.getOutputStream();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(os, "UTF-8"), true);
        String LF = "\n";		// line feed
        String fileName = uploadFile.getName();
        out.append("--" + boundary).append(LF);
        out.append("Content-Disposition: form-data; name=\"image\"; filename=\"" + fileName + "\"").append(LF);
        out.append("Content-Type: "  + URLConnection.guessContentTypeFromName(fileName)).append(LF);
        out.append(LF);
        out.flush();
        
        // 실제 파일을 읽어서 전송
        FileInputStream fis = new FileInputStream(uploadFile);
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = fis.read(buffer)) != -1) 
        	os.write(buffer, 0, bytesRead);		// buffer의 처음부터 읽은 데이터 수만큼
        fis.close();
        os.flush();
        out.append(LF);
        out.append("--" + boundary + "--").append(LF).flush();
        out.close();
        
        // 응답 결과 확인
 		int responseCode = conn.getResponseCode();
     	if (responseCode != 200)
     		System.out.println("error!!!!!!! responseCode= " + responseCode);
     	
 		// 결과 확인
 		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
 		StringBuffer sb = new StringBuffer();
 		String line;
 		while ((line = br.readLine()) != null) 
 			sb.append(line);
 		br.close();
 		
 		
 		
		return sb.toString();
	}
}





