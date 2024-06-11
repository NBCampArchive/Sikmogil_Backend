package com.examle.sikmogilbackend.gcs;

import com.examle.sikmogilbackend.global.template.RspTemplate;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/imageUpload")
@RequiredArgsConstructor
public class GcsController {

    private final GcsService gcsService;

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public RspTemplate<String> update(
            Authentication authentication,
            @RequestParam String directory,
            @ModelAttribute List<MultipartFile> image
    ) throws IOException {
        log.info("dto = " + directory);
        gcsService.uploadGCS(authentication.getName(), directory, image);
        return new RspTemplate<>(HttpStatus.OK, "사진 등록 성공");
    }
}
