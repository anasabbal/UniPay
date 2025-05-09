package com.unipay.response;



public class EmailConfirmationResponse {
    private String success;
    private String code;
    private String message;

    public EmailConfirmationResponse(String success, String message) {
        this.success = success;
        this.message = message;
    }

    public EmailConfirmationResponse(String success, String code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
