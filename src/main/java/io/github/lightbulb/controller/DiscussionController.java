package io.github.lightbulb.controller;

import io.github.lightbulb.model.entity.Discussion;
import io.github.lightbulb.service.DiscussionRepository;
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
@RequestMapping("/threads")
@ExposesResourceFor(Discussion.class)
public class DiscussionController {

  private final DiscussionRepository discussionRepository;

  @Autowired
  public DiscussionController(DiscussionRepository discussionRepository) {
    this.discussionRepository = discussionRepository;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Discussion> post(@RequestBody Discussion discussion) {
    discussionRepository.save(discussion);
    return ResponseEntity.created(discussion.getHref()).body(discussion);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Discussion> get() {
    return discussionRepository.getAllByOrderByCreatedDesc();
  }
}
