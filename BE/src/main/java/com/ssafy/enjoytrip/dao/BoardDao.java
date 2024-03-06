package com.ssafy.enjoytrip.dao;

import java.sql.SQLException;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.dto.*;


@Mapper
public interface BoardDao {

	void writeArticle(BoardDto boardDto) throws SQLException;
	void registerFile(BoardDto boardDto) throws Exception;
	
	// key(subject,userId, articleNo) , word, start, listsize
	List<BoardDto> listArticle(Map<String, String> map) throws SQLException;
	
	// key(subject, userId, articleNo), word
	int getTotalArticleCount(Map<String, String> param) throws SQLException;
	
	BoardDto getArticle(int articleNo) throws SQLException;
	
	void updateHit(int articleNo) throws SQLException;
	void modifyArticle(BoardDto boardDto) throws SQLException;
	
	void deleteFile(int articleNo) throws Exception;
	void deleteArticle(int articleNo) throws SQLException;
	List<FileInfoDto> fileInfoList(int articleNo) throws Exception;
	
	
}
