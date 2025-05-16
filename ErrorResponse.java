package com.xcelore.doctorpatient.dto;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private LocalDateTime timestamp;
    private HttpStatus status;
    private String message;
    private String path;
}