package com.examle.sikmogilbackend.community.comment.api;

import com.examle.sikmogilbackend.community.comment.api.dto.request.CommentSaveReqDto;
import com.examle.sikmogilbackend.community.comment.api.dto.request.CommentUpdateReqDto;
import com.examle.sikmogilbackend.community.comment.api.dto.response.CommentInfoResDto;
import com.examle.sikmogilbackend.community.comment.api.dto.response.CommentListResDto;
import com.examle.sikmogilbackend.community.comment.application.CommentService;
import com.examle.sikmogilbackend.global.template.RspTemplate;
import com.examle.sikmogilbackend.global.util.PageableUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "댓글 등록", description = "댓글을 등록 합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "등록 성공"),
            @ApiResponse(responseCode = "401", description = "인증실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
    })
    @PostMapping("/")
    public RspTemplate<String> commentSave(@AuthenticationPrincipal String email,
                                           @RequestBody CommentSaveReqDto commentSaveReqDto) {
        commentService.commentSave(email, commentSaveReqDto);
        return new RspTemplate<>(HttpStatus.CREATED, "댓글 등록",
                String.format("%d 게시글의 댓글입니다.", commentSaveReqDto.boardId()));
    }

    @Operation(summary = "댓글 수정", description = "댓글을 수정 합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 수정"),
            @ApiResponse(responseCode = "401", description = "인증실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
    })
    @PutMapping("/{commentId}")
    public RspTemplate<CommentInfoResDto> commentUpdate(@AuthenticationPrincipal String email,
                                                        @PathVariable("commentId") Long commentId,
                                                        @RequestBody CommentUpdateReqDto commentUpdateReqDto) {
        return new RspTemplate<>(HttpStatus.OK, "댓글 수정",
                commentService.commentUpdate(email, commentId, commentUpdateReqDto));
    }

    @Operation(summary = "댓글 삭제", description = "댓글을 삭제 합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 삭제"),
            @ApiResponse(responseCode = "401", description = "인증실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
    })
    @DeleteMapping("/{commentId}")
    public RspTemplate<String> commentDelete(@AuthenticationPrincipal String email,
                                             @PathVariable("commentId") Long commentId) {
        commentService.commentDelete(email, commentId);
        return new RspTemplate<>(HttpStatus.OK, "댓글 삭제", "댓글이 삭제되었습니다.");
    }

    @Operation(summary = "게시글 댓글 조회", description = "게시글에 있는 댓글을 조회 합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
    })
    @GetMapping("/{boardId}")
    public RspTemplate<CommentListResDto> commentAll(@AuthenticationPrincipal String email,
                                                     @PathVariable(name = "boardId") Long boardId,
                                                     @Parameter(name = "page", description = "댓글 page", in = ParameterIn.QUERY)
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @Parameter(name = "size", description = "댓글 page size", in = ParameterIn.QUERY)
                                                     @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageableUtil.of(page, size);
        CommentListResDto commentListResDto = commentService.commentAll(email, boardId, pageable);
        return new RspTemplate<>(HttpStatus.OK, "댓글 조회", commentListResDto);
    }

}
