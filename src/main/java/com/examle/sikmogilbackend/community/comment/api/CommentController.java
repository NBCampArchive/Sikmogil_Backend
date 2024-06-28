package com.examle.sikmogilbackend.community.comment.api;

import com.examle.sikmogilbackend.community.comment.api.dto.request.CommentSaveReqDto;
import com.examle.sikmogilbackend.community.comment.application.CommentService;
import com.examle.sikmogilbackend.global.template.RspTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
        Long commentId = commentService.commentSave(email, commentSaveReqDto);
        return new RspTemplate<>(HttpStatus.CREATED, "댓글 등록",
                String.format("%d 게시글의 댓글입니다.", commentSaveReqDto.boardId()));
    }


}
