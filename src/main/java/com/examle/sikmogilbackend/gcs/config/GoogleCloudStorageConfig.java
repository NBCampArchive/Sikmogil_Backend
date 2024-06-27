package com.examle.sikmogilbackend.gcs.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class GoogleCloudStorageConfig {
    @Value("${myapp.local-url}")
    private String localUrl;
    @Value("${spring.cloud.project-id}") // application.yml에 써둔 bucket 이름
    private String projectId;

    @Value("${spring.cloud.gcp.storage.credentials.location}") // application.yml에 써둔 bucket 이름
    private String keyJson;

    @Bean
    public Storage storage() throws IOException {

        ClassPathResource resource = new ClassPathResource(keyJson);
        GoogleCredentials credentials = GoogleCredentials.fromStream(resource.getInputStream());
        return StorageOptions.newBuilder()
                .setProjectId(projectId)
                .setCredentials(credentials)
                .build()
                .getService();
    }
}
