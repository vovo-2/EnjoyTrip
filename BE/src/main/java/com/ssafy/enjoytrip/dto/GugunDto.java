package com.ssafy.enjoytrip.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GugunDto {
	@Schema(description = "구군코드")
	private int gugunCode;
	
	@Schema(description = "구군이름")
	private String gugunName;
	
	@Schema(description = "시도코드")
	private int sidoCode;
	public GugunDto(int gugunCode,String gugunName) {
		this.gugunCode = gugunCode;
		this.gugunName = gugunName;
	}


}
