package edu.cnm.deepdive.lightbulb.view;

import java.net.URI;
import java.util.Date;
import java.util.UUID;
import org.springframework.lang.NonNull;

/***
 * This interface gets the id, creates the date, gets the string text, gets the href and gets name.
 */
public interface FlatComment {

  @NonNull
  UUID getId();

  @NonNull
  Date getCreated();

  @NonNull
  Date getUpdated();

  @NonNull
  String getText();

  @NonNull
  URI getHref();
}


