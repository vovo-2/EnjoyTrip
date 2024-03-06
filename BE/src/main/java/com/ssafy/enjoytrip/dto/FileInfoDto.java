package com.ssafy.enjoytrip.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class FileInfoDto {
	private String saveFolder;
	private String originalFile;
	private String saveFile;
	
	public FileInfoDto(String saveFolder, String originalFile, String saveFile) {
		super();
		this.saveFolder = saveFolder;
		this.originalFile = originalFile;
		this.saveFile = saveFile;
	}
	
}
