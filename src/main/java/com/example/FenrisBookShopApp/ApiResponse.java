package com.example.FenrisBookShopApp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Setter
public class ApiResponse<T> {
    private HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private final LocalDateTime timestamp;

    private String message;
    private String debugMessage;
    private Collection<T> data;

    public ApiResponse() {
        timestamp = LocalDateTime.now();
    }

    public ApiResponse(HttpStatus status, String message, @NotNull Throwable exception) {
        this();
        this.status = status;
        this.message = message;
        debugMessage = exception.getLocalizedMessage();
    }
}
