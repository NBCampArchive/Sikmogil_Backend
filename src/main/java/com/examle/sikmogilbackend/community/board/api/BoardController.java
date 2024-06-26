package com.examle.sikmogilbackend.community.board.api;

import com.examle.sikmogilbackend.community.board.api.dto.request.BoardSaveReqDto;
import com.examle.sikmogilbackend.community.board.application.BoardService;
import com.examle.sikmogilbackend.global.template.RspTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @Operation(summary = "게시글 등록", description = "게시글 등록 합니다")
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

    // 게시글 조회 (전체, 다이어트, 운동, 자유)

    // 게시글 상세 조회

    // 게시글 삭제

    // 게시글 수정
}
