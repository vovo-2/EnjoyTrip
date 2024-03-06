package com.ssafy.enjoytrip.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.enjoytrip.dto.ApiResponseDto;
import com.ssafy.enjoytrip.dto.BoardDto;
import com.ssafy.enjoytrip.dto.BoardFormDto;
import com.ssafy.enjoytrip.dto.BoardListDto;
import com.ssafy.enjoytrip.dto.FileInfoDto;
import com.ssafy.enjoytrip.service.BoardServcie;
import com.ssafy.enjoytrip.util.FileStore;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/board")
@CrossOrigin(origins = "http://localhost:5173")
public class BoardController {
	private BoardServcie boardService;
	private FileStore imageStore;
	
	public BoardController(BoardServcie boardService, FileStore imageStore) {
		super();
		this.boardService = boardService;
		this.imageStore = imageStore;
	}

	@PostMapping
	@ApiOperation(value = "게시판 글작성", notes = "새로운 게시글 정보를 입력한다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "게시글 작성 성공", content = @Content(schema = @Schema(implementation = ApiResponseDto.class))),
			@ApiResponse(responseCode = "400", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ApiResponseDto.class))) })
	@io.swagger.annotations.ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(response = ApiResponseDto.class, message = "게게시글 작성 성공", code = 200),
			@io.swagger.annotations.ApiResponse(response = ApiResponseDto.class, message = "존재하지 않는 리소스 접근", code = 400) })
	public ResponseEntity<?> writeArticle( @ApiParam(value = "Required. ", required = true) BoardFormDto boardFormDto) {
		try {
			
			BoardDto boardDto = new BoardDto();
			boardDto.setSubject(boardFormDto.getSubject());
			boardDto.setContent(boardFormDto.getContent());
			boardDto.setUserId(boardFormDto.getUserId());
			boardDto.setUserName(boardFormDto.getUserName());
			
			// 이미지 처리 로직 START
			if(boardFormDto.getImageFiles()!=null) {
				try {
					List<FileInfoDto> images  = imageStore.storeImages(boardFormDto.getImageFiles());
					boardDto.setImageInfos(images);
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}
			// 이미지 처리 로직 END
			
			boardService.writeArticle(boardDto);
			ApiResponseDto response = new ApiResponseDto(true, 0, "success");
			return new ResponseEntity<ApiResponseDto>(response, HttpStatus.OK);
		} catch (SQLException e) {
			e.printStackTrace();
			ApiResponseDto response = new ApiResponseDto(false, ((SQLException) e).getErrorCode(), e.getMessage());
			return new ResponseEntity<ApiResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping
	@ApiOperation(value = "게시판 글목록", notes = "모든 게시글의 정보를 반환한다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "게시글 조회 성공", content = @Content(schema = @Schema(implementation = BoardListDto.class))),
			@ApiResponse(responseCode = "400", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ApiResponseDto.class))) })
	@io.swagger.annotations.ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(response = BoardListDto.class, message = "게시글 조회 성공", code = 200),
			@io.swagger.annotations.ApiResponse(response = ApiResponseDto.class, message = "존재하지 않는 리소스 접근", code = 400) })
	public ResponseEntity<?> getAllArticleList(@RequestParam @ApiParam(value="Optional. key, word, lastArticleNo, listSize") Map<String, String> parameterMap) {

		try {
			System.out.println(parameterMap);
			BoardListDto list = boardService.listArticle(parameterMap);
			return new ResponseEntity<BoardListDto>(list, HttpStatus.OK);
		} catch (SQLException e) {
			e.printStackTrace();
			ApiResponseDto response = new ApiResponseDto(false, e.getErrorCode(), e.getMessage());
			return new ResponseEntity<ApiResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/{articleno}")
	@ApiOperation(value = "게시판 글보기", notes = "글번호에 해당하는 게시글의 정보를 반환한다.", response = BoardDto.class)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "게시글 조회 성공", content = @Content(schema = @Schema(implementation = BoardDto.class))),
			@ApiResponse(responseCode = "400", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ApiResponseDto.class))) })
	@io.swagger.annotations.ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(response = BoardListDto.class, message = "게시글 조회 성공", code = 200),
			@io.swagger.annotations.ApiResponse(response = ApiResponseDto.class, message = "존재하지 않는 리소스 접근", code = 400) })
	public ResponseEntity<?> getArticle(@PathVariable("articleno") @ApiParam(value = "Required. ") int articleNo) {
		try {
			BoardDto boardDto = boardService.getArticle(articleNo);
			boardService.updateHit(articleNo);
			return new ResponseEntity<BoardDto>(boardDto, HttpStatus.OK);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ResponseEntity<String>("ERROR", HttpStatus.BAD_REQUEST);

		}
	}

	@GetMapping("/modify/{articleno}")
	@ApiOperation(value = "수정 할 글 얻기", notes = "글번호에 해당하는 게시글의 정보를 반환한다.", response = BoardDto.class)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "수정 할 데이터 반환 성공", content = @Content(schema = @Schema(implementation = BoardDto.class))),
			@ApiResponse(responseCode = "400", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ApiResponseDto.class))) })
	@io.swagger.annotations.ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(response = BoardListDto.class, message = "수정 할 데이터 반환 성공", code = 200),
			@io.swagger.annotations.ApiResponse(response = ApiResponseDto.class, message = "존재하지 않는 리소스 접근", code = 400) })	
	public ResponseEntity<BoardDto> getModifyArticle(
			@PathVariable("articleno") @ApiParam(value = "Required. ") int articleNo) throws Exception {
		return new ResponseEntity<BoardDto>(boardService.getArticle(articleNo), HttpStatus.OK);
	}

	@PutMapping
	@ApiOperation(value = "게시판 글수정", notes = "수정할 게시글 정보를 입력한다.")
	public ResponseEntity<String> modifyArticle(BoardFormDto boardFormDto) throws Exception {

		BoardDto boardDto = new BoardDto();
		boardDto.setSubject(boardFormDto.getSubject());
		boardDto.setContent(boardFormDto.getContent());
		boardDto.setArticleNo(boardFormDto.getArticleNo());
		// 이미지 처리 로직 START
		try {
			List<FileInfoDto> images  = imageStore.storeImages(boardFormDto.getImageFiles());
			boardDto.setImageInfos(images);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 이미지 처리 로직 END
		
		
		boardService.modifyArticle(boardDto);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{articleno}")
	@ApiOperation(value = "게시판 글삭제", notes = "글번호에 해당하는 게시글의 정보를 삭제한다.", response = String.class)
	public ResponseEntity<String> deleteArticle(
			@PathVariable("articleno") @ApiParam(value = "Required. ") int articleNo) throws Exception {
		boardService.deleteArticle(articleNo);
		return ResponseEntity.ok().build();

	}

}
