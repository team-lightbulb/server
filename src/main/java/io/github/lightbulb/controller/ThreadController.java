package io.github.lightbulb.controller;

import io.github.lightbulb.model.entity.Thread;
import io.github.lightbulb.service.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quotes")

public class ThreadController {

  private final ThreadRepository threadRepository;

  @Autowired
  public ThreadController(ThreadRepository threadRepository) {
    this.threadRepository = threadRepository;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Thread> post(@RequestBody Thread thread) {
    threadRepository.save(thread);
    return ResponseEntity.created(thread.getHref()).body(thread);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Thread> get() {
    return threadRepository.getAllByOrderByCreatedDesc();
  }


}
