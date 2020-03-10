package edu.cnm.deepdive.lightbulb.model.repository;

import edu.cnm.deepdive.lightbulb.model.entity.Comment;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, UUID> {

  Iterable<Comment> getAllByOrderByCreatedDesc();

  Iterable<Comment> getAllByTextContainsOrderByTextAsc(String fragment);

  default Comment findOrFail(UUID id) {
    return findById(id).get();
  }
}
