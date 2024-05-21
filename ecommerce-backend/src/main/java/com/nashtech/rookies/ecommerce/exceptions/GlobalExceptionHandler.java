package com.nashtech.rookies.ecommerce.exceptions;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
  private static final String ERROR_LOG_FORMAT = "Error: URI: {}, ErrorCode: {}, Message: {}";

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleNotFoundException(ResourceNotFoundException ex, WebRequest request) {
    String message = ex.getMessage();
    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.toString(),
        HttpStatus.NOT_FOUND.getReasonPhrase(), message);
    log.warn(ERROR_LOG_FORMAT, this.getServletPath(request), 404, message);
    log.debug(ex.toString());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex, WebRequest request) {
    String message = ex.getMessage();
    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.toString(),
        HttpStatus.BAD_REQUEST.getReasonPhrase(), message);
    return ResponseEntity.badRequest().body(errorResponse);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
    List<String> errors = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(error -> error.getField() + " " + error.getDefaultMessage())
        .toList();

    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.toString(),
        HttpStatus.BAD_REQUEST.getReasonPhrase(), "Request information is not valid", errors);
    return ResponseEntity.badRequest().body(errorResponse);
  }

  private String getServletPath(WebRequest webRequest) {
    ServletWebRequest servletRequest = (ServletWebRequest) webRequest;
    return servletRequest.getRequest().getServletPath();
  }
}
