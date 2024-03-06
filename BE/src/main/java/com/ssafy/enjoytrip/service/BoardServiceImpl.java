package com.ssafy.enjoytrip.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.dao.BoardDao;
import com.ssafy.enjoytrip.dto.BoardDto;
import com.ssafy.enjoytrip.dto.BoardListDto;

@Service
public class BoardServiceImpl implements BoardServcie {
	private BoardDao boardDao;

	public BoardServiceImpl(BoardDao boardDao) {
		super();
		this.boardDao = boardDao;
	}

	@Override
	public void writeArticle(BoardDto boardDto) throws SQLException {
		boardDao.writeArticle(boardDto);
		if(boardDto.getImageInfos()!=null) {
			try {
				boardDao.registerFile(boardDto);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public BoardListDto listArticle(Map<String, String> map) throws SQLException {
		
		if(map.get("key")==null) map.put("key","-1");
		if(map.get("word")==null) map.put("word","-1");
		if(map.get("lastArticleNo")==null) map.put("lastArticleNo","-1");
		if(map.get("listSize")==null) map.put("listSize", "10");
		
		
		List<BoardDto> list = boardDao.listArticle(map);

		BoardListDto boardListDto = new BoardListDto();
		boardListDto.setArticles(list);
		boardListDto.setTotalArticleCount(boardDao.getTotalArticleCount(map));
		
		boardListDto.setLastArticleNo(0);
		if(!list.isEmpty()) {
			int lastArticleNo = Integer.MAX_VALUE;
			for(BoardDto article: list) {
				lastArticleNo = Math.min(lastArticleNo, article.getArticleNo());
			}
			
			boardListDto.setLastArticleNo(lastArticleNo);
		}
		
		return boardListDto;
	}

	@Override
	public BoardDto getArticle(int articleNo) throws SQLException {
		return boardDao.getArticle(articleNo);
	}

	@Override
	public void updateHit(int articleNo) throws SQLException {
		boardDao.updateHit(articleNo);
	}

	@Override
	public void modifyArticle(BoardDto boardDto) throws SQLException {
		try {
			boardDao.deleteFile(boardDto.getArticleNo());
			boardDao.registerFile(boardDto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boardDao.modifyArticle(boardDto);
	}

	@Override
	public void deleteArticle(int articleNo) throws SQLException {
		try {
			boardDao.deleteFile(articleNo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boardDao.deleteArticle(articleNo);
	}

}
