package com.nashtech.rookies.ecommerce.exceptions;

import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
  @ExceptionHandler({ ResourceNotFoundException.class })
  protected ResponseEntity<ErrorResponse> handleResourceNotFoundException(
      RuntimeException exception, WebRequest request) {
    var error = ErrorResponse.builder().code(HttpStatus.NOT_FOUND.value())
        .message(exception.getMessage()).build();
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
      HttpStatusCode status, WebRequest request) {
    var errors = ex.getBindingResult().getAllErrors()
        .stream()
        .map(e -> (FieldError) e)
        .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
    var error = ErrorResponse.builder().code(HttpStatus.BAD_REQUEST.value())
        .message("Validation Error").errors(errors).build();
    return ResponseEntity.badRequest().body(error);
  }

  // @ExceptionHandler(ResourceNotFoundException.class)
  // @ResponseStatus(HttpStatus.NOT_FOUND)
  // public ResponseEntity<ErrorResponse>
  // handleResourceNotFoundException(ResourceNotFoundException ex,
  // WebRequest request) {
  // ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
  // ex.getMessage(), null);
  // return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  // }

  // @ExceptionHandler(MethodArgumentNotValidException.class)
  // @ResponseStatus(HttpStatus.BAD_REQUEST)
  // public ResponseEntity<ErrorResponse>
  // handleValidationExceptions(MethodArgumentNotValidException ex) {
  // Map<String, String> errors = new HashMap<>();
  // ex.getBindingResult().getAllErrors().forEach((error) -> {
  // String fieldName = ((FieldError) error).getField();
  // String errorMessage = error.getDefaultMessage();
  // errors.put(fieldName, errorMessage);
  // });
  // ErrorResponse errorResponse = new
  // ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation Failed", errors);
  // return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  // }

  // @ExceptionHandler(Exception.class)
  // public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex,
  // WebRequest request) {
  // ErrorResponse errorResponse = new
  // ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(),
  // null);
  // return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  // }
}
