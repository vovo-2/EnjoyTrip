package com.ssafy.enjoytrip.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.dto.*;




@Mapper
public interface AttractionDao {

	List<AttractionInfoDto> attractionList(Map<String, String> parameterMap);
	List<AttractionInfoDto> searchByTitle(String title, int sidoCode);

}
