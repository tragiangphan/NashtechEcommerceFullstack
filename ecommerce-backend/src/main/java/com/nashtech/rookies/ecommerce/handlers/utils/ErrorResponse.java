package com.nashtech.rookies.ecommerce.handlers.utils;

import java.util.ArrayList;
import java.util.List;

public record ErrorResponse(String statusCode, String title, List<String> fieldErrors) {
  public ErrorResponse(String statusCode, String title) {
    this(statusCode, title, new ArrayList<>());
  }
}
