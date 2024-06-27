package com.examle.sikmogilbackend.gcs.application;

import com.examle.sikmogilbackend.community.board.domain.Board;
import com.examle.sikmogilbackend.community.board.domain.repository.BoardPictureRepository;
import com.examle.sikmogilbackend.community.board.domain.repository.BoardRepository;
import com.examle.sikmogilbackend.community.board.exception.BoardNotFoundException;
import com.examle.sikmogilbackend.gcs.api.dto.DeleteBoardImageReqDto;
import com.examle.sikmogilbackend.member.domain.Member;
import com.examle.sikmogilbackend.member.domain.repository.MemberRepository;
import com.examle.sikmogilbackend.member.exception.MemberNotFoundException;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GcsService {

    private final Storage storage;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final BoardPictureRepository boardPictureRepository;

    @Transactional
    public List<String> uploadGCS(String email, String directory, List<MultipartFile> images) throws IOException {
        List<String> result = new ArrayList<>();
        // !!!!!!!!!!!이미지 업로드 관련 부분!!!!!!!!!!!!!!!
        for (MultipartFile image : images) {
            log.info("image = " + image.getOriginalFilename());
            String uuid =
                    directory + "/" + email + "/" + image.getOriginalFilename(); // Google Cloud Storage에 저장될 파일 이름
            String ext = image.getContentType(); // 파일의 형식 ex) JPG

            // Cloud에 이미지 업로드
            BlobInfo blobInfo = storage.create(
                    BlobInfo.newBuilder("sikmogil", uuid)
                            .setContentType(ext)
                            .build(),
                    image.getInputStream()
            );
            result.add("https://storage.googleapis.com/sikmogil/" + directory + "/" + email + "/"
                    + image.getOriginalFilename());
        }
        return result;
    }

    @Transactional
    public void deleteBoardImage(String email, String directory, DeleteBoardImageReqDto deleteBoardImageReqDto) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        Board board = boardRepository.findById(deleteBoardImageReqDto.boardId())
                .orElseThrow(BoardNotFoundException::new);

        boardPictureRepository.deleteByBoardAndImageUrlIn(board, deleteBoardImageReqDto.imageUrl());

        for (String url : deleteBoardImageReqDto.imageUrl()) {
            String blobName = directory + "/" + email + "/" + extractFileNameFromUrl(url);
            BlobId blobId = BlobId.of("sikmogil", blobName);
            storage.delete(blobId);
        }
    }

    private String extractFileNameFromUrl(String imageUrl) {
        return imageUrl.substring(imageUrl.lastIndexOf('/') + 1);
    }

}
