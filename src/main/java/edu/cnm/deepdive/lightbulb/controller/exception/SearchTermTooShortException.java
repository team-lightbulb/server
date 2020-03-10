package edu.cnm.deepdive.lightbulb.controller.exception;

/**
 *  This method throws an IllegalAr
 */

public class SearchTermTooShortException extends IllegalArgumentException {

  public SearchTermTooShortException() {
    super();
  }

  public SearchTermTooShortException(String s) {
    super(s);
  }

  public SearchTermTooShortException(String message, Throwable cause) {
    super(message, cause);
  }

  public SearchTermTooShortException(Throwable cause) {
    super(cause);
  }

}
