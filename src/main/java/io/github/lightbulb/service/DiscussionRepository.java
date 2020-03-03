package io.github.lightbulb.service;

import io.github.lightbulb.model.entity.Discussion;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscussionRepository extends JpaRepository<Discussion, UUID> {

  Iterable<Discussion> getAllByOrderByCreatedDesc();

  Iterable<Discussion> getAllByTextContainsOrderByTextAsc(String fragment);

  default Discussion findOrFail(UUID id) {
    return findById(id).get();
  }
}
