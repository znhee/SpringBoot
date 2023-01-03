package com.mulcam.demo.controller;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mulcam.demo.entity.FileEntity;

@Component
@RequestMapping("/file")
public class FileController {

	@GetMapping("/upload")
	public String uploadForm() {
		return "file/upload";
	}
	
	@PostMapping("/upload") 
	public String upload(MultipartHttpServletRequest req, Model model) {
		String msg = req.getParameter("msg");
		List<MultipartFile> files = req.getFiles("files");
		List<FileEntity> list = new ArrayList<>();
		
		for (MultipartFile file: files) {
			FileEntity fe = new FileEntity(file.getOriginalFilename(), file.getContentType());
			list.add(fe);
			
			File fileName = new File(file.getOriginalFilename());
			try {
				file.transferTo(fileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		model.addAttribute("msg", msg);
		model.addAttribute("uploadFiles", list);
		return "file/result";
	}
	
	@Value("${spring.servlet.multipart.location}")
	String uploadDir;
	
	@GetMapping("/download")
	public ResponseEntity<Resource> download(@ModelAttribute FileEntity fe) {
		Path path = Paths.get(uploadDir + "/" + fe.getFileName());
		try {
			String contentType = Files.probeContentType(path);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentDisposition(
				ContentDisposition.builder("attachment")
					.filename(fe.getFileName(), StandardCharsets.UTF_8)
					.build()
			);
			headers.add(HttpHeaders.CONTENT_TYPE, contentType);
			Resource resource = new InputStreamResource(Files.newInputStream(path));
			return new ResponseEntity<>(resource, headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}