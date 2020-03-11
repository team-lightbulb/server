package edu.cnm.deepdive.lightbulb.controller.exception;

import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/***
 * This method sends a bad request response if a exception is caught.
 */
@RestControllerAdvice
public class ControllerExceptionHandler {


  /***
   * This method sends a bad request response if a exception is caught.
   */
  @ExceptionHandler(SearchTermTooShortException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Search term too short")
  public void tooShort() {}


  /***
   * Sends exception if resource not found if and exception is caught.
   */
  @ExceptionHandler(NoSuchElementException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource not found")
  public void notFound() {}

  /***
   * This method sends a bad request response if a exception is caught.
   */
  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid request content or parameters")
  public void badRequest() {}

}
