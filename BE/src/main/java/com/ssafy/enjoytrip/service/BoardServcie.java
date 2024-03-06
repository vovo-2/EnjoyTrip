package com.ssafy.enjoytrip.service;

import java.sql.SQLException;
import java.util.Map;

import com.ssafy.enjoytrip.dto.BoardDto;
import com.ssafy.enjoytrip.dto.BoardListDto;

public interface BoardServcie {
	void writeArticle(BoardDto boardDto) throws SQLException;
	
	// key(subject,userId, articleNo) , word, start, listsize
	BoardListDto listArticle(Map<String, String> map) throws SQLException;
	
	BoardDto getArticle(int articleNo) throws SQLException;
	
	void updateHit(int articleNo) throws SQLException;
	void modifyArticle(BoardDto boardDto) throws SQLException;
	
	void deleteArticle(int articleNo) throws SQLException;
	
}
