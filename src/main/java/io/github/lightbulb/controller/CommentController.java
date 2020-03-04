package io.github.lightbulb.controller;

import io.github.lightbulb.model.entity.Comment;
import io.github.lightbulb.service.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comments")
@ExposesResourceFor(Comment.class)
public class CommentController {

  private final CommentRepository commentRepository;

  @Autowired
  public CommentController(CommentRepository commentRepository) {
    this.commentRepository = commentRepository;
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
}
