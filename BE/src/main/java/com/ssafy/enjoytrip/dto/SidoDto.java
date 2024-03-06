package com.ssafy.enjoytrip.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SidoDto {

	private int sidoCode;
	private String sidoName;
	public SidoDto(int sidoCode,String sidoName) {
		this.sidoCode = sidoCode;
		this.sidoName = sidoName;
	}


}
