package com.examle.sikmogilbackend.gcs;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GcsService {

    private final Storage storage;


    @Transactional
    public void uploadGCS(String email, String directory, List<MultipartFile> images) throws IOException {
        // !!!!!!!!!!!이미지 업로드 관련 부분!!!!!!!!!!!!!!!
        for (MultipartFile image : images) {
            log.info("image = " + image.getOriginalFilename());
            String uuid = directory+"/"+email+ "/"+image.getOriginalFilename(); // Google Cloud Storage에 저장될 파일 이름
            String ext = image.getContentType(); // 파일의 형식 ex) JPG

            // Cloud에 이미지 업로드
            BlobInfo blobInfo = storage.create(
                    BlobInfo.newBuilder("sikmogil", uuid)
                            .setContentType(ext)
                            .build(),
                    image.getInputStream()
            );
        }
    }

}
