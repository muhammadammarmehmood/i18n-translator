package com.i18n.translator.model.Dto.Response;

import lombok.Data;

@Data
public class ErrorResponse {
    private String message;
    private int status;

    public ErrorResponse(String message, int status) {
        this.message = message;
        this.status = status;
    }
}