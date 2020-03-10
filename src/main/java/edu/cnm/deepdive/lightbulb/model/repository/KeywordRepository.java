package edu.cnm.deepdive.lightbulb.model.repository;

import edu.cnm.deepdive.lightbulb.model.entity.Keyword;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<Keyword, UUID> {

  Iterable<Keyword> getAllByOrderByCreatedDesc();

  Iterable<Keyword> getAllByNameContainsOrderByNameAsc(String fragment);

  default Keyword findOrFail(UUID id) {
    return findById(id).get();
  }
}
