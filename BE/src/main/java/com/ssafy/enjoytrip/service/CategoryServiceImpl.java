package com.ssafy.enjoytrip.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.dao.CategoryDao;
import com.ssafy.enjoytrip.dto.GugunDto;
import com.ssafy.enjoytrip.dto.SidoDto;

@Service
public class CategoryServiceImpl implements CategoryService {
	private CategoryDao categoryDao;

	public CategoryServiceImpl(CategoryDao categoryDao) {
		super();
		this.categoryDao = categoryDao;
	}

	@Override
	public List<SidoDto> sidoList() {
		return categoryDao.sidoList();
	}

	@Override
	public List<GugunDto> gugunList(int sidoCode) {
		return categoryDao.gugunList(sidoCode);
	}

	@Override
	public List<Integer> contentTypeIdList() {
		return categoryDao.contentTypeIdList();
	}
	
	
}
