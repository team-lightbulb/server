package io.github.lightbulb.model.repository;

import io.github.lightbulb.model.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, UUID> {

  Iterable<User> getAllByOrderByCreatedDesc();

  Iterable<User> getAllByTextContainsOrderByTextAsc(String fragment);

  @Query(value = "SELECT * FROM sa.USER_CLIENT ORDER BY RANDOM() OFFSET 0 ROWS FETCH NEXT 1 ROW ONLY",
      nativeQuery = true)
  Optional<User> getRandom();

  default User findOrFail(UUID id) {
    return findById(id).get();
  }

}
