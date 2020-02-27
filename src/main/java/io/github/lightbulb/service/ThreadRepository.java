package io.github.lightbulb.service;

import io.github.lightbulb.model.entity.Thread;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ThreadRepository extends JpaRepository<Thread, UUID> {

  Iterable<Thread> getAllByOrderByCreatedDesc();

  Iterable<Thread> getAllByTextContainsOrderByTextAsc(String fragment);

  @Query(value = "SELECT * FROM sa.Quote ORDER BY RANDOM() OFFSET 0 ROWS FETCH NEXT 1 ROW ONLY",
      nativeQuery = true)
  Optional<Thread> getRandom();

  default Thread findOrFail(UUID id) {
    return findById(id).get();
  }


}
