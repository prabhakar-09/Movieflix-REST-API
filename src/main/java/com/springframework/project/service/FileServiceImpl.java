package com.springframework.project.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ch.qos.logback.classic.Logger;
import lombok.extern.java.Log;

@Service
@Log
public class FileServiceImpl implements FileService{

	@Override
	public String uploadFile(String path, MultipartFile file) throws IOException {
		
//		Getting name of the file 
		String fileName = file.getOriginalFilename();
		
//		Getting file path
		String filePath = path + File.separator + fileName;
		
//		Creating file object
		File f = new File(path);
		
		if(!f.exists()) {
			f.mkdir();
		}
		
//		Copy or upload file to the path
		Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
		
		return fileName;
	}

	@Override
	public InputStream getResourceFile(String path, String fileName) throws FileNotFoundException {
		
		String filePath = path + File.separator + fileName;
		
		log.info(filePath);
		
		return new FileInputStream(filePath); // 
	}

}
