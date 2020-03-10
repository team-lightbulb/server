package edu.cnm.deepdive.lightbulb.model.repository;

import edu.cnm.deepdive.lightbulb.model.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, UUID> {

  Iterable<User> getAllByOrderByCreatedDesc();

  Iterable<User> getAllByTextContainsOrderByTextAsc(String fragment);

  default User findOrFail(UUID id) {
    return findById(id).get();
  }
}
