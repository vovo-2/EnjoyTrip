package com.ssafy.enjoytrip.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.enjoytrip.dto.FileInfoDto;

@Component
public class FileStore {
	// root 경로(user.dir -> 현재 디렉토리)
	private final String rootPath = System.getProperty("user.dir");
	// root 경로에 있는 images 디렉토리
	private final String fileDir = rootPath+"\\src\\main\\resources\\images\\";
	
	public String getFullPath(String filename) {
		return fileDir+filename;
	}
	
	public FileInfoDto storeImage(MultipartFile multipartFile) throws IOException{
		if(multipartFile.isEmpty()) return null;
		
		String originalFileName = multipartFile.getOriginalFilename();
		String uuidFileName = UUID.randomUUID()+"."+extractExt(originalFileName);
		
		multipartFile.transferTo(new File(getFullPath(uuidFileName)));
		
		return new FileInfoDto(fileDir, originalFileName, uuidFileName);
		
	}
	
	public List<FileInfoDto> storeImages(List<MultipartFile> images) throws IOException{
		List<FileInfoDto> list = new ArrayList<>();
		for(MultipartFile image: images) {
			if(!image.isEmpty()) list.add(storeImage(image));
		}
		
		return list;
	}
	private String extractExt(String fileName) {
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos+1);
	}
	
}
