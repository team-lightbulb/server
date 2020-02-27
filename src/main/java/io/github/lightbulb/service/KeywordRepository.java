package io.github.lightbulb.service;

import io.github.lightbulb.model.entity.Keyword;
import io.github.lightbulb.model.entity.Thread;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface KeywordRepository extends JpaRepository<Keyword, UUID> {

  Iterable<Keyword> getAllByOrderByCreatedDesc();

  Iterable<Keyword> getAllByTextContainsOrderByTextAsc(String fragment);

  @Query(value = "SELECT * FROM sa.Quote ORDER BY RANDOM() OFFSET 0 ROWS FETCH NEXT 1 ROW ONLY",
      nativeQuery = true)
  Optional<Keyword> getRandom();

  default Keyword findOrFail(UUID id) {
    return findById(id).get();
  }



}
