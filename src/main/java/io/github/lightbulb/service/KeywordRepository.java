package io.github.lightbulb.service;

import io.github.lightbulb.model.entity.Keyword;
import io.github.lightbulb.model.entity.Thread;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface KeywordRepository extends JpaRepository<Keyword, UUID> {

  Iterable<Keyword> getAllByOrderByCreatedDesc();

  Iterable<Keyword> getAllByNameContainsOrderByNameAsc(String fragment);

  default Keyword findOrFail(UUID id) {
    return findById(id).get();
  }



}
