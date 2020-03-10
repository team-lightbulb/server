package edu.cnm.deepdive.lightbulb.controller.exception;

import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * The controller exception handler throws an exception when there is an invalid request.
 */

@RestControllerAdvice
public class ControllerExceptionHandler {


  /***
   * Throws exception if search term is too short.
   */
  @ExceptionHandler(SearchTermTooShortException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Search term too short")
  public void tooShort() {}


  /***
   * Throws exception if resource not found.
   */
  @ExceptionHandler(NoSuchElementException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource not found")
  public void notFound() {}

  /***
   * Throws exception if invalid request content or parameters.
   */
  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid request content or parameters")
  public void badRequest() {}

}
