package com.example.FenrisBookShopApp.data;

import com.example.FenrisBookShopApp.entities.book.BookFileEntity;
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

@Service
public class ResourceStorage {
    @Value("${upload.path}")
    private String uploadPath;

    @Value("${download.path}")
    private String downloadPath;

    public String uploadBookImage(@NotNull MultipartFile file, String slug) throws IOException {
        String resourceURI = null;

        if (!file.isEmpty()) {
            if (!new File(uploadPath).exists()) {
                Files.createDirectories(Paths.get(uploadPath));
                Logger.getLogger(this.getClass().getSimpleName()).info("directory created");
            }

            String filename = slug + '.' + FilenameUtils.getExtension(file.getOriginalFilename());
            Path path = Paths.get(uploadPath, filename);
            resourceURI = "/books-covers/" + filename;
            file.transferTo(path);
            Logger.getLogger(this.getClass().getSimpleName()).info(filename + " uploaded");
        }

        return resourceURI;
    }

    public Path getBookFilePath(@NotNull BookFileEntity bookFile) {
        return Paths.get(bookFile.getPath());
    }

    public MediaType getBookFileMime(BookFileEntity bookFile) {
        String mimeType = URLConnection.guessContentTypeFromName(getBookFilePath(bookFile).getFileName().toString());

        if (mimeType != null) {
            return MediaType.parseMediaType(mimeType);
        }

        return MediaType.APPLICATION_OCTET_STREAM;
    }

    public byte[] getBookFileByteArray(@NotNull BookFileEntity bookFile) throws IOException {
        Path path = Paths.get(downloadPath, bookFile.getPath());
        return Files.readAllBytes(path);
    }
}
