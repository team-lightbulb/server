package io.github.lightbulb.service;

import io.github.lightbulb.model.entity.Thread;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ThreadRepository extends JpaRepository<Thread, UUID> {

  Iterable<Thread> getAllByOrderByCreatedDesc();

  Iterable<Thread> getAllByTextContainsOrderByTextAsc(String fragment);

  default Thread findOrFail(UUID id) {
    return findById(id).get();
  }


}
