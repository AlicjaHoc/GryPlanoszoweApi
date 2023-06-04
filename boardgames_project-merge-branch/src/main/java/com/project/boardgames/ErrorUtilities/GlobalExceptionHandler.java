package com.project.boardgames.ErrorUtilities;

import com.project.boardgames.utilities.RequestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AppException.class)
    public ResponseEntity<RequestResponse<Object>> handleAppException(AppException ex) {
        RequestResponse<Object> response = new RequestResponse<>(false, null, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getStatusCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RequestResponse<Object>> handleException(Exception ex) {
        RequestResponse<Object> response = new RequestResponse<>(false, ex, "An error occurred while processing the request.");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
