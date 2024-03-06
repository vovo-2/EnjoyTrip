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

@Schema(description="api response 정보 dto")
public class ApiResponseDto {
	@Schema(description = "처리 success 여부", example="false")
	private boolean success;
	@Schema(description = "error code", example="200")
	private int code;
	@Schema(description = "e.toString()", example="badSqlGrammerCodes")
	private String message;
	
	public ApiResponseDto(boolean success, int code, String message) {
		super();
		this.success = success;
		this.code = code;
		this.message = message;
	}
	
	
}
