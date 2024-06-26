package com.examle.sikmogilbackend.community.board.api;

import com.examle.sikmogilbackend.community.board.api.dto.request.BoardSaveReqDto;
import com.examle.sikmogilbackend.community.board.api.dto.response.BoardInfoResDto;
import com.examle.sikmogilbackend.community.board.api.dto.response.BoardListResDto;
import com.examle.sikmogilbackend.community.board.application.BoardService;
import com.examle.sikmogilbackend.global.template.RspTemplate;
import com.examle.sikmogilbackend.global.util.PageableUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @Operation(summary = "게시글 등록", description = "게시글 등록 합니다, Request의 Category는 (ALL, DIET, WORKOUT, FREE) 이와같이 작성해야함.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "등록 성공"),
            @ApiResponse(responseCode = "401", description = "인증실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
    })
    @PostMapping("/")
    public RspTemplate<String> boardSave(@AuthenticationPrincipal String email,
                                         BoardSaveReqDto boardSaveReqDto) {
        Long boardId = boardService.boardSave(email, boardSaveReqDto);
        return new RspTemplate<>(HttpStatus.CREATED, "게시글 등록", String.format("%d번 게시글 등록!", boardId));
    }

    @Operation(summary = "카테고리별 게시글 목록 조회", description = "카테고리별 게시글 목록을 조회 합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
    })
    @GetMapping("/{category}")
    public RspTemplate<BoardListResDto> categoryByBoardAll(@AuthenticationPrincipal String email,
                                                           @Parameter(name = "category", description = "게시글 카테고리(ALL, DIET, WORKOUT, FREE) 이와같이 작성해야함.", in = ParameterIn.PATH)
                                                           @PathVariable String category,
                                                           @Parameter(name = "page", description = "게시물 page", in = ParameterIn.QUERY)
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @Parameter(name = "size", description = "게시물 page size", in = ParameterIn.QUERY)
                                                           @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageableUtil.of(page, size);
        BoardListResDto boards = boardService.categoryByBoardAll(email, category, pageable);
        return new RspTemplate<>(HttpStatus.OK, "카테고리별 게시물 조회", boards);
    }

    @Operation(summary = "게시글 상세 조회", description = "게시글을 상세 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
    })
    @GetMapping("/detail/{boardId}")
    public RspTemplate<BoardInfoResDto> boardDetail(@AuthenticationPrincipal String email,
                                                    @PathVariable(name = "boardId") Long boardId) {
        return new RspTemplate<>(HttpStatus.OK, "게시글 상세 조회", boardService.boardDetail(email, boardId));
    }

    // 게시글 삭제

    // 게시글 수정

    // 게시글 좋아요

    // 게시글 좋아요 취소
}
