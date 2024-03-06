package com.ssafy.enjoytrip.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class BoardListDto {

	private List<BoardDto> articles;
	private int totalArticleCount;
	private int lastArticleNo;
}
