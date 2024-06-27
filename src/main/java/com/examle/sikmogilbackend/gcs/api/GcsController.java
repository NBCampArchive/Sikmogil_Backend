package com.examle.sikmogilbackend.gcs.api;

import com.examle.sikmogilbackend.gcs.api.dto.DeleteBoardImageReqDto;
import com.examle.sikmogilbackend.gcs.application.GcsService;
import com.examle.sikmogilbackend.global.template.RspTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class GcsController {

    private final GcsService gcsService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public RspTemplate<List<String>> update(
            Authentication authentication,
            @RequestParam String directory,
            @ModelAttribute List<MultipartFile> image
    ) throws IOException {
        log.info("dto = " + directory);
        List<String> result = gcsService.uploadGCS(authentication.getName(), directory, image);
        return new RspTemplate<>(HttpStatus.OK, "사진 등록 성공", result);
    }

    @Operation(summary = "게시글 이미지 삭제", description = "게시글의 이미지를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "401", description = "인증실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
    })
    @DeleteMapping("/delete")
    public RspTemplate<String> deleteBoardImage(@AuthenticationPrincipal String email,
                                                @RequestParam String directory,
                                                @RequestBody DeleteBoardImageReqDto deleteBoardImageReqDto) {
        gcsService.deleteBoardImage(email, directory, deleteBoardImageReqDto);
        return new RspTemplate<>(HttpStatus.OK, "게시글 이미지 삭제");
    }
}
