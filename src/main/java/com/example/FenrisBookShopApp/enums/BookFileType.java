package com.example.FenrisBookShopApp.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum BookFileType {
    PDF(".pdf"),
    EPUB(".epub"),
    FB2(".fb2");

    private final String fileExtension;

    public static String getFileExtensionByTypeId(int typeId) {
        return switch (typeId) {
            case 1 -> EPUB.fileExtension;
            case 2 -> FB2.fileExtension;
            case 3 -> PDF.fileExtension;
            default -> "";
        };
    }
}
