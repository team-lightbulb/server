package edu.cnm.deepdive.lightbulb.controller.exception;

/***
 * Throws illegal argument exception.
 */
public class SearchTermTooShortException extends IllegalArgumentException {


  /***
   * Throws illegal argument exception.
   */
  public SearchTermTooShortException() {
    super();
  }


  /***
   * Throws illegal argument exception.
   */
  public SearchTermTooShortException(String s) {
    super(s);
  }


  /***
   * Throws illegal argument exception.
   */
  public SearchTermTooShortException(String message, Throwable cause) {
    super(message, cause);
  }


  /***
   * Throws illegal argument exception.
   */
  public SearchTermTooShortException(Throwable cause) {
    super(cause);
  }

}
