package com.nashtech.rookies.ecommerce.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class ImageUploadUtil {
  
  public static String saveFile(String path, String fileName, MultipartFile multipartFile) throws IOException {
    Path uploadPath = Paths.get(path);

    // Ensure the upload directory exists
    if (!Files.exists(uploadPath)) {
      try {
        Files.createDirectories(uploadPath);
      } catch (IOException e) {
        throw new IOException("Could not create directory: " + path, e);
      }
    }

    String fileCode = UUID.randomUUID().toString();

    try (var inputStream = multipartFile.getInputStream()) {
      Path filePath = uploadPath.resolve(fileCode + "-" + fileName);
      Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException ioe) {
      throw new IOException("Could not save file: " + fileName, ioe);
    }

    return fileCode;
  }

  public static void deleteFile(String pathFileName) throws IOException {
    Path deletePath = Paths.get(pathFileName);

    // Ensure the upload directory exists
    if (Files.exists(deletePath)) {
      try {
        Files.delete(deletePath);
      } catch (IOException e) {
        throw new IOException("Could not delete directory: " + pathFileName, e);
      }
    }
  }
}
