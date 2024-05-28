package com.nashtech.rookies.ecommerce.handlers.exceptions;

import com.nashtech.rookies.ecommerce.handlers.utils.MessageUtils;

public class ResourceConflictException extends RuntimeException {
    private String message;

    public ResourceConflictException(String errorCode, Object... var2) {
        this.message = MessageUtils.getMessage(errorCode, var2);
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
