package com.ssafy.enjoytrip.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.dto.ApiResponseDto;
import com.ssafy.enjoytrip.dto.AttractionInfoDto;
import com.ssafy.enjoytrip.dto.BoardListDto;
import com.ssafy.enjoytrip.dto.GugunDto;
import com.ssafy.enjoytrip.dto.SidoDto;
import com.ssafy.enjoytrip.service.AttractionService;
import com.ssafy.enjoytrip.service.CategoryService;

import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/attr")
@CrossOrigin(origins = "http://localhost:5173")
public class AttractionController {

	private AttractionService attractionService;
	private CategoryService categoryService;

	public AttractionController(AttractionService attractionService, CategoryService categoryService) {
		super();
		this.attractionService = attractionService;
		this.categoryService = categoryService;
	}

	@GetMapping("/search")
	@Operation(summary="관광지 정보 조회", description = "검색조건(시도,구군,관광지유형)에 따른 관광지 정보 조회")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "관광지 조회 성공", content = @Content(schema = @Schema(implementation = AttractionInfoDto.class))),
			@ApiResponse(responseCode = "400", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ApiResponseDto.class))) })
	@io.swagger.annotations.ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(response = AttractionInfoDto.class, message = "게시글 조회 성공", code = 200),
			@io.swagger.annotations.ApiResponse(response = ApiResponseDto.class, message = "존재하지 않는 리소스 접근", code = 400) })
	public ResponseEntity<?> search(@RequestParam 
			@ApiParam(value = "Optional. sidoCode: String, gugunCode: String, contentTypeId: String, key: String[title, addr], word: String")Map<String, String> map)
			throws SQLException {
		
		if(map.get("sidoCode")==null) map.put("sidoCode", "-1");
		if(map.get("gugunCode")==null) map.put("gugunCode", "-1");
		if(map.get("contentTypeId")==null) map.put("contentTypeId", "-1");
		
		if(map.get("key")==null) map.put("key", "-1");
 		if(map.get("key").equals("addr")) map.put("key", "addr1");
 		
 		if(map.get("word")==null) map.put("word","");
			
		List<AttractionInfoDto> resultDto = attractionService.attractionList(map);
		return new ResponseEntity<List<AttractionInfoDto>>(resultDto, HttpStatus.OK);

	}

	@GetMapping("/sido")
	@Operation(summary="시도코드 반환", description = "관광지 정보 검색에 사용되는 시도코드 반환")
	public ResponseEntity<?> getSidoList() throws SQLException {
		List<SidoDto> list = categoryService.sidoList();
		return new ResponseEntity<List<SidoDto>>(list, HttpStatus.OK);
	}

	@GetMapping("/gugun")
	@Operation(summary="구군코드 반환", description = "시도코드에 따른 구군코드 반환")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "구군코드 반환 성공", content = @Content(schema = @Schema(implementation = GugunDto.class))),
			@ApiResponse(responseCode = "400", description = "시도코드 parameter 필요", content = @Content(schema = @Schema(implementation = ApiResponseDto.class))) })
	@io.swagger.annotations.ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(response = BoardListDto.class, message = "게시글 조회 성공", code = 200),
			@io.swagger.annotations.ApiResponse(response = ApiResponseDto.class, message = "존재하지 않는 리소스 접근", code = 400) })
	public ResponseEntity<?> getSidoList(@RequestParam @ApiParam(value="시도 코드") int sido){
		try {
			List<GugunDto> list = categoryService.gugunList(sido);
			return new ResponseEntity<List<GugunDto>>(list, HttpStatus.OK);
		}catch(SQLException e) {
			ApiResponseDto response = new ApiResponseDto(false, e.getErrorCode(),e.getMessage());
			return new ResponseEntity<ApiResponseDto>(response, HttpStatus.BAD_REQUEST);
		}
		
	}

	@GetMapping("/content")
	@Operation(summary="관광지 유형 코드 반환", description = "관광지 유형 코드 반환")
	public ResponseEntity<?> getContentTypeIdList() throws SQLException {
		List<Integer> list = categoryService.contentTypeIdList();
		return new ResponseEntity<List<Integer>>(list, HttpStatus.OK);
	}

}
