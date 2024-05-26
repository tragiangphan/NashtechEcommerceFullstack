package com.nashtech.rookies.ecommerce.handlers;

import java.util.List;

import com.nashtech.rookies.ecommerce.handlers.exceptions.ResourceConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import com.nashtech.rookies.ecommerce.handlers.exceptions.BadRequestException;
import com.nashtech.rookies.ecommerce.handlers.exceptions.NotFoundException;
import com.nashtech.rookies.ecommerce.handlers.utils.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
  private static final String ERROR_LOG_FORMAT = "Error: URI: {}, ErrorCode: {}, Message: {}";

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex, WebRequest request) {
    List<String> errors = List.of(ex.getMessage());

    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.toString(),
        HttpStatus.NOT_FOUND.getReasonPhrase(), errors);

    log.warn(ERROR_LOG_FORMAT, this.getServletPath(request), HttpStatus.NOT_FOUND, errors);
    log.debug(ex.toString());

    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(BadRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex, WebRequest request) {
    List<String> errors = List.of(ex.getMessage());

    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.toString(),
        HttpStatus.BAD_REQUEST.getReasonPhrase(), errors);

    log.warn(ERROR_LOG_FORMAT, this.getServletPath(request), HttpStatus.BAD_REQUEST, errors);

    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ResourceConflictException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ResponseEntity<ErrorResponse> handleResourceExistException(ResourceConflictException ex, WebRequest request) {
    List<String> errors = List.of(ex.getMessage());

    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.toString(),
            HttpStatus.CONFLICT.getReasonPhrase(), errors);

    log.warn(ERROR_LOG_FORMAT, this.getServletPath(request), HttpStatus.CONFLICT, errors);

    return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
    List<String> errors = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(error -> error.getField() + " " + error.getDefaultMessage())
        .toList();

    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.toString(),
        HttpStatus.BAD_REQUEST.getReasonPhrase(), errors);

    log.warn(ERROR_LOG_FORMAT, this.getServletPath(request), HttpStatus.BAD_REQUEST, errors);

    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  private String getServletPath(WebRequest webRequest) {
    ServletWebRequest servletRequest = (ServletWebRequest) webRequest;
    return servletRequest.getRequest().getServletPath();
  }
}
