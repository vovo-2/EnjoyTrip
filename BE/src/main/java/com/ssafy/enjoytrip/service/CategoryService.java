package com.ssafy.enjoytrip.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.enjoytrip.dto.GugunDto;
import com.ssafy.enjoytrip.dto.SidoDto;

public interface CategoryService {
	
	List<SidoDto> sidoList();
	List<GugunDto> gugunList(int sidoCode) throws SQLException;
	List<Integer> contentTypeIdList();

}
