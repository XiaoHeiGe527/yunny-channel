package com.yunny.channel.common.exception;

public class PermissionException extends RuntimeException {
    private String redirectUrl;

    public PermissionException(String message, String redirectUrl) {
        super(message);
        this.redirectUrl = redirectUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }
}