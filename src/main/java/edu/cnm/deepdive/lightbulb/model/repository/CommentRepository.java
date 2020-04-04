package edu.cnm.deepdive.lightbulb.model.repository;

import edu.cnm.deepdive.lightbulb.model.entity.Comment;
import edu.cnm.deepdive.lightbulb.model.entity.Keyword;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


/***
 * This interface extends comment repository to jpa repository.
 */
public interface CommentRepository extends JpaRepository<Comment, UUID> {

  Iterable<Comment> getAllByKeywordsContaining(Keyword keyword);

  Iterable<Comment> getAllByOrderByCreatedDesc();

  Iterable<Comment> getAllByTextContainsOrNameContainsOrderByNameAsc(String textFragment, String nameFragment);

  default Comment findOrFail(UUID id) {
    return findById(id).get();
  }
}
