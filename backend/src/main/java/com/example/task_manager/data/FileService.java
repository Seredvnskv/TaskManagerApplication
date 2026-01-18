package com.example.task_manager.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Service
public class FileService {
    private static final Logger log = LoggerFactory.getLogger(FileService.class);
    @Getter
    private final ObjectMapper mapper;

    public FileService(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public <T> void writeToFile(T data, Path filePath) throws IOException {
        try {
            String jsonData = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(data);

            Files.write(filePath, jsonData.getBytes(),
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            log.info("Successfully wrote data to file: {}", filePath);
        }
        catch (IOException e) {
            log.error("Failed to write data to file: {}", filePath, e);
            throw e;
        }
    }

    public <T> T readFromFile(Path filePath, Class<T> clazz) throws IOException {
        try {
            if (!Files.exists(filePath)) {
                log.warn("File does not exist: {}", filePath);
                throw new IOException("File not found: " + filePath);
            }

            String jsonData = Files.readString(filePath);
            T data = mapper.readValue(jsonData, clazz);

            log.info("Successfully read data from file: {}", filePath);

            return data;
        }
        catch (Exception e) {
            log.error("Failed to read data from file: {}", filePath, e);
            throw e;
        }
    }
}
