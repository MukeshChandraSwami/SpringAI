package com.learn.media_generator.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileUtils {

    public static String uploadFile(byte[] file, String uploadDir, String name) throws IOException {
        // Ensure upload directory exists
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Generate unique filename
        String fileName = UUID.randomUUID() + "_" + name;
        Path filePath = Paths.get(uploadDir, fileName);

        // Save file locally
        Files.write(filePath, file);

        // Build file URL (served later by ResourceHandler)
        return  "/files/" + fileName;
    }
}
