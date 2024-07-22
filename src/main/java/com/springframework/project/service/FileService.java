package com.springframework.project.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

//								   is used when dealing with the files in spring boot
	String uploadFile(String path, MultipartFile file) throws IOException;
	
	InputStream getResourceFile(String path, String fileName) throws FileNotFoundException;
	
	
}
