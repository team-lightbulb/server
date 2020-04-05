package edu.cnm.deepdive.lightbulb.controller.rest;

import edu.cnm.deepdive.lightbulb.controller.exception.SearchTermTooShortException;
import edu.cnm.deepdive.lightbulb.model.entity.Comment;
import edu.cnm.deepdive.lightbulb.model.entity.Keyword;
import edu.cnm.deepdive.lightbulb.model.entity.User;
import edu.cnm.deepdive.lightbulb.model.repository.CommentRepository;
import edu.cnm.deepdive.lightbulb.model.repository.KeywordRepository;
import edu.cnm.deepdive.lightbulb.model.repository.UserRepository;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


/***
 * This class contains the comment repository.
 */
@RestController
@RequestMapping("/comments")
@ExposesResourceFor(Comment.class)
public class CommentController {

  private final CommentRepository commentRepository;
  private final KeywordRepository keywordRepository;
  private final UserRepository userRepository;


  /***
   * This method will link to the other repositories.
   * @param commentRepository
   * @param keywordRepository
   * @param userRepository
   */
  @Autowired
  public CommentController(CommentRepository commentRepository,
      KeywordRepository keywordRepository,
      UserRepository userRepository) {
    this.commentRepository = commentRepository;
    this.keywordRepository = keywordRepository;
    this.userRepository = userRepository;
  }


  /***
   * This method references the comments to the user.
   * @param comment
   * @return
   */
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Comment> post(@RequestBody Comment comment, Authentication auth) {
    User user = (User) auth.getPrincipal();
    Comment reference = (comment.getReference() != null) ? commentRepository
        .findOrFail(comment.getReference().getId()) : null;
    comment.setReference(reference);
    comment.setUser(user);
    if (comment.getKeywords() != null) {
      List<Keyword> keywords = new LinkedList<>();
      for (Keyword keyword : comment.getKeywords()) {
        keywords.add(keywordRepository.findById(keyword.getId()).orElse(keyword));
      }
      comment.getKeywords().clear();
      comment.getKeywords().addAll(keywords);
    }
    commentRepository.save(comment);
    return ResponseEntity.created(comment.getHref()).body(comment);
  }


  /***
   * This method arranges comments in descending order.
   * @return
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Comment> get() {
    return commentRepository.getAllByOrderByCreatedDesc();
  }

  /***
   * Throws and exception if there is a bad request or invalid search.
   * @param fragment
   * @return
   */
  @GetMapping(value = "/search", params = "q", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Comment> search(@RequestParam("q") String fragment) {
    if (fragment.length() < 3) {
      throw new SearchTermTooShortException();
    }
    return keywordRepository.findFirstByName(fragment)
        .map((keyword) ->
            commentRepository.getAllByKeywordsContainingOrTextContainsOrNameContainsOrderByNameAsc(
                keyword, fragment, fragment))
        .orElse(
            commentRepository.getAllByTextContainsOrNameContainsOrderByNameAsc(fragment, fragment));
  }


  /***
   * This method returns comment repository.
   * @param id
   * @return
   */
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Comment get(@PathVariable UUID id) {
    return commentRepository.findOrFail(id);
  }

  /***
   * This method returns modified text.
   * @param id
   * @param modifiedComment
   * @return
   */
  @PutMapping(value = "/{id}/text",
      consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
  public String put(@PathVariable UUID id, @RequestBody String modifiedComment) {
    Comment comment = commentRepository.findOrFail(id);
    comment.setText(modifiedComment);
    commentRepository.save(comment);
    return comment.getText();
  }


  /***
   * This method deletes comments repositories.
   * @param id
   */
  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    commentRepository.findById(id).ifPresent(commentRepository::delete);
  }
}

