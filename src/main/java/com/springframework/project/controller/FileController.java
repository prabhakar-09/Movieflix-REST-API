package com.springframework.project.controller;

import java.awt.PageAttributes.MediaType;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.StreamUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springframework.project.service.FileService;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/file/")
public class FileController {

//	@Autowired
	private final FileService fileService;
	
	@Value("${project.poster}")
	private String path;
	
//	Constructor injection
	public FileController(FileService fileService) {
		this.fileService = fileService;
	}
	
	@PostMapping("/upload")
	public ResponseEntity<String> uploadFileHandler(@RequestPart MultipartFile file) throws IOException{
		
		String uploadedFileName = fileService.uploadFile(path, file);
		
		return ResponseEntity.ok("File Uploaded " + uploadedFileName);
		
	}
	
//	@GetMapping("/{fileName}")
//	public void serveFileHandler(@PathVariable String fileName, HttpServletResponse response) throws FileNotFoundException {
//		InputStream resourceFile = fileService.getResourceFile(path, fileName);
//		
//		response.setContentType("image/png");
//		
//		StreamUtils.copy(resourceFile, response.getOutputStream());
//	}
	
	 @GetMapping("/{fileName}")
	    public void serveFileHandler(@PathVariable String fileName, HttpServletResponse response) throws IOException {
	        InputStream resourceFile = fileService.getResourceFile(path, fileName);
	        
	        response.setContentType("image/png");
	        
	        ServletOutputStream outputStream = response.getOutputStream();
	        byte[] buffer = new byte[1024];
	        int bytesRead;
	        while ((bytesRead = resourceFile.read(buffer)) != -1) {
	            outputStream.write(buffer, 0, bytesRead);
	        }
	        
	        outputStream.flush();
	        outputStream.close();
	        resourceFile.close();
	    }
	
}
