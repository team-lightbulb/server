package io.github.lightbulb.view;

import java.net.URI;
import java.util.Date;
import java.util.UUID;
import org.springframework.lang.NonNull;

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


