package com.example.FenrisBookShopApp.controllers;

import com.example.FenrisBookShopApp.data.ResourceStorage;
import com.example.FenrisBookShopApp.entities.book.BookFileEntity;
import com.example.FenrisBookShopApp.services.book.BookFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.nio.file.Path;

@Controller
@RequestMapping("books/download")
public class DownloadBookController {
    private final BookFileService bookFileService;
    private final ResourceStorage resourceStorage;

    @Autowired
    public DownloadBookController(BookFileService bookFileService, ResourceStorage resourceStorage) {
        this.bookFileService = bookFileService;
        this.resourceStorage = resourceStorage;
    }

    @GetMapping(value = "{hash}", name = "app.book.download_book")
    public ResponseEntity<ByteArrayResource> downloadBookFile(@PathVariable String hash) throws IOException {
        BookFileEntity bookFile = bookFileService.findBookFileEntitiesByHash(hash);
        Path path = resourceStorage.getBookFilePath(bookFile);
        MediaType mediaType = resourceStorage.getBookFileMime(bookFile);
        byte[] data = resourceStorage.getBookFileByteArray(bookFile);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
                .contentType(mediaType)
                .contentLength(data.length)
                .body(new ByteArrayResource(data));
    }
}
