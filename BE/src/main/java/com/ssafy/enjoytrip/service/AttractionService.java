package com.ssafy.enjoytrip.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.enjoytrip.dto.AttractionInfoDto;

public interface AttractionService {
	List<AttractionInfoDto> attractionList(Map<String, String> parameterMap ) throws SQLException;
	List<AttractionInfoDto> searchByTitle(String title, int sidoCode) throws SQLException;
}
