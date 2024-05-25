package com.nashtech.rookies.ecommerce.exceptions;

import com.nashtech.rookies.ecommerce.exceptions.utils.MessageUtils;

public class ResourceNotFoundException extends RuntimeException {
  private String message;

  public ResourceNotFoundException(String errorCode, Object... var2) {
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
