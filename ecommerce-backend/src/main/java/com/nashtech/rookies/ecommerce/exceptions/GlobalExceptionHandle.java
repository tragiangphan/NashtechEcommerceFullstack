package com.nashtech.rookies.ecommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandle extends ResponseEntityExceptionHandler {
  @ExceptionHandler({ ResourceNotFoundException.class })
  protected ResponseEntity<ErrorResponse> handleResourceNotFoundException(
      RuntimeException exception, WebRequest request) {
    var error = ErrorResponse.builder().code(HttpStatus.NOT_FOUND.value())
        .message(exception.getMessage()).build();
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }
}
