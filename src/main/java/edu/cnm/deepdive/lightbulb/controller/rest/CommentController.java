package edu.cnm.deepdive.lightbulb.controller.rest;

import edu.cnm.deepdive.lightbulb.controller.exception.SearchTermTooShortException;
import edu.cnm.deepdive.lightbulb.model.entity.Comment;
import edu.cnm.deepdive.lightbulb.model.entity.User;
import edu.cnm.deepdive.lightbulb.model.repository.CommentRepository;
import edu.cnm.deepdive.lightbulb.model.repository.KeywordRepository;
import edu.cnm.deepdive.lightbulb.model.repository.UserRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("/comments")
@ExposesResourceFor(Comment.class)
public class CommentController {

  private final CommentRepository commentRepository;
  private final KeywordRepository keywordRepository;
  private final UserRepository userRepository;


  /***
   *
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
   *
   * @param comment
   * @return
   */
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Comment> post(@RequestBody Comment comment) {
    User user = userRepository.findOrFail(comment.getUser().getId());
    Comment reference = (comment.getReference() != null) ? commentRepository.findOrFail(comment.getReference().getId()) : null;
    comment.setReference(reference);
    comment.setUser(user);
    commentRepository.save(comment);
    return ResponseEntity.created(comment.getHref()).body(comment);
  }


  /***
   *
   * @return
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Comment> get() {
    return commentRepository.getAllByOrderByCreatedDesc();
  }

  /***
   *
   * @param fragment
   * @return
   */
  @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Comment> search(@RequestParam("q") String fragment) {
    if (fragment.length() < 3) {
      throw new SearchTermTooShortException();
    }
    return commentRepository.getAllByTextContainsOrderByTextAsc(fragment);
  }


  /***
   *
   * @param id
   * @return
   */
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Comment get(@PathVariable UUID id) {
    return commentRepository.findOrFail(id);
  }


  /***
   *
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
   *
   * @param id
   */
  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    commentRepository.findById(id).ifPresent(commentRepository::delete);
  }
}

