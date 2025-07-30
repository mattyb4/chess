package ui;

import com.google.gson.Gson;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ResponseException extends Exception {
  private final int statusCode;

  public ResponseException(int statusCode, String message) {
    super(message);
    this.statusCode = statusCode;
  }

  public String toJson() {
    return new Gson().toJson(Map.of("message", getMessage(), "status", statusCode));
  }

  public static ResponseException fromJson(InputStream stream) {
    try {
      var map = new Gson().fromJson(new InputStreamReader(stream), HashMap.class);
      Object statusObj = map.get("status");
      int status = (statusObj instanceof Double) ? ((Double) statusObj).intValue() : 500;
      String message = map.getOrDefault("message", "Unknown error").toString();
      return new ResponseException(status, message);
    } catch (Exception e) {
      return new ResponseException(500, "Internal Server Error");
    }
  }

  public int StatusCode() {
    return statusCode;
  }
}
