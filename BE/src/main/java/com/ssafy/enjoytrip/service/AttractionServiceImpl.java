package com.ssafy.enjoytrip.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.dao.AttractionDao;
import com.ssafy.enjoytrip.dto.AttractionInfoDto;
@Service
public class AttractionServiceImpl implements AttractionService {

	private AttractionDao attractionDao;
	
	public AttractionServiceImpl(AttractionDao attractionDao) {
		super();
		this.attractionDao = attractionDao;
	}

	@Override
	public List<AttractionInfoDto> attractionList(Map<String, String> parameterMap) throws SQLException{
		return attractionDao.attractionList(parameterMap);
	}

	@Override
	public List<AttractionInfoDto> searchByTitle(String title, int sidoCode) throws SQLException{
		return null;
	}

}
