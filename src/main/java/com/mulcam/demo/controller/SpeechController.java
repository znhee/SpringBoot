package com.mulcam.demo.controller;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

@Controller
@RequestMapping("/speech")
public class SpeechController {
	
	@Value("${etriKey}") private String etriKey;
	
	@GetMapping("/audio")
	public String audio() {
		return "speech/audio";
	}
	
	@ResponseBody
	@GetMapping("/etri")
	public String etri() throws Exception {
		String openApiURL = "http://aiopen.etri.re.kr:8000/WiseASR/Recognition";
        String accessKey = etriKey;    // 발급받은 API Key
        String languageCode = "korean";     // 언어 코드
        String audioFilePath = "/tmp/hello.wav";  // 녹음된 음성 파일 경로
        String audioContents = null;
		
        Gson gson = new Gson();
        
        Map<String, Object> request = new HashMap<>();
        Map<String, String> argument = new HashMap<>();
        
        Path path = Paths.get(audioFilePath);
        byte[] audioBytes = Files.readAllBytes(path);
        audioContents = Base64.getEncoder().encodeToString(audioBytes);
        
        // POST data/parameter 전송
        argument.put("language_code", languageCode);
        argument.put("audio", audioContents);
        request.put("argument", argument);
        
        URL url = new URL(openApiURL);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setRequestProperty("Authorization", accessKey);

        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.write(gson.toJson(request).getBytes("UTF-8"));
        wr.flush();
        wr.close();

        int responseCode = conn.getResponseCode();
        InputStream is = conn.getInputStream();
        byte[] buffer = new byte[is.available()];
        int byteRead = is.read(buffer);
        String responBody = new String(buffer);

        String data = "[responseCode] " + responseCode + "<br>"
        			+ "[responBody]" + "<br>" + responBody;
        
		return data;
	}
}





