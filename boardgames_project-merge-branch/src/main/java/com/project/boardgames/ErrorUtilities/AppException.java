package com.project.boardgames.ErrorUtilities;

import org.springframework.beans.factory.annotation.Value;

public class AppException extends RuntimeException {

    private final int statusCode;
    private final String status;
    private final boolean isOperational;
    private final String message;
    private boolean isStackIncluded;

    public AppException(String message, int statusCode, String status, boolean isOperational) {
        super(message);
        this.message = message;
        this.statusCode = statusCode;
        this.status = status;
        this.isOperational = isOperational;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatus() {
        return status;
    }

    public boolean isOperational() {
        return isOperational;
    }

    @Override
    public String getMessage() {
        return message;
    }



}
