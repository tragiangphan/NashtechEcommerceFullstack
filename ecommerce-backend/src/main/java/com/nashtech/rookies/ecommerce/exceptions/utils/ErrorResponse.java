package com.nashtech.rookies.ecommerce.exceptions.utils;

import java.util.ArrayList;
import java.util.List;

public record ErrorResponse(String statusCode, String title, String detail, List<String> fieldErrors) {
  public ErrorResponse(String statusCode, String title, String detail) {
    this(statusCode, title, detail, new ArrayList<>());
  }
}
