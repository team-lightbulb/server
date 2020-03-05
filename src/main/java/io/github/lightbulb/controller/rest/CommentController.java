package io.github.lightbulb.controller.rest;

import io.github.lightbulb.controller.exception.SearchTermTooShortException;
import io.github.lightbulb.model.entity.Comment;
import io.github.lightbulb.model.repository.CommentRepository;
import io.github.lightbulb.model.repository.KeywordRepository;
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

  @Autowired
  public CommentController(CommentRepository commentRepository,
      KeywordRepository keywordRepository) {
    this.commentRepository = commentRepository;
    this.keywordRepository = keywordRepository;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Comment> post(@RequestBody Comment comment) {
    commentRepository.save(comment);
    return ResponseEntity.created(comment.getHref()).body(comment);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Comment> get() {
    return commentRepository.getAllByOrderByCreatedDesc();
  }

  @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Comment> search(@RequestParam("q") String fragment) {
    if (fragment.length() < 3) {
      throw new SearchTermTooShortException();
    }
    return commentRepository.getAllByTextContainsOrderByTextAsc(fragment);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Comment get(@PathVariable UUID id) {
    return commentRepository.findOrFail(id);
  }

  @PutMapping(value = "/{id}/text",
      consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
  public String put(@PathVariable UUID id, @RequestBody String modifiedQuote) {
    Comment comment = commentRepository.findOrFail(id);
    comment.setText(modifiedQuote);
    commentRepository.save(comment);
    return comment.getText();
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    commentRepository.findById(id).ifPresent(commentRepository::delete);
  }
}

