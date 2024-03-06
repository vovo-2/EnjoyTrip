package com.ssafy.enjoytrip.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.dto.*;


@Mapper
public interface CategoryDao {
	
	List<SidoDto> sidoList();
	List<GugunDto> gugunList(int sidoCode);
	List<Integer> contentTypeIdList();
}
