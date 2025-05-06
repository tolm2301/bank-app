package com.tolm.userservice.web.exception;

import java.time.LocalDateTime;

public record ErrorDetails(
        String message,
        String details,
        LocalDateTime timestamp
) {
}
