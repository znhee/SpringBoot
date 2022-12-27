package com.mulcam.demo.entity;

public class FileEntity {
	private String fileName;
	private String contentType;
	
	public FileEntity() {}
	public FileEntity(String fileName, String contentType) {
		this.fileName = fileName;
		this.contentType = contentType;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	
}
