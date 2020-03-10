package edu.cnm.deepdive.lightbulb.model.repository;

import edu.cnm.deepdive.lightbulb.model.entity.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {

  Iterable<User> getAllByOrderByCreatedDesc();

  Iterable<User> getAllByDisplayNameContainsOrderByDisplayName(String fragment);

  default User findOrFail(UUID id) {
    return findById(id).get();
  }
}
