package edu.cnm.deepdive.lightbulb.controller.exception;


/***
 *
 */
public class SearchTermTooShortException extends IllegalArgumentException {


  /***
   *
   */
  public SearchTermTooShortException() {
    super();
  }


  /***
   *
   * @param s
   */
  public SearchTermTooShortException(String s) {
    super(s);
  }


  /***
   *
   * @param message
   * @param cause
   */
  public SearchTermTooShortException(String message, Throwable cause) {
    super(message, cause);
  }


  /***
   *
   * @param cause
   */
  public SearchTermTooShortException(Throwable cause) {
    super(cause);
  }

}
