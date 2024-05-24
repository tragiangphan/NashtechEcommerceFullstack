package com.nashtech.rookies.ecommerce.exceptions;

public class RequirementNotFoundException extends RuntimeException {
    private String message;

    public RequirementNotFoundException(String errorCode, Object... var2) {
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
