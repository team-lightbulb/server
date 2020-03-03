package io.github.lightbulb.controller;

import io.github.lightbulb.model.entity.Discussion;
import io.github.lightbulb.model.entity.Keyword;
import io.github.lightbulb.service.KeywordRepository;
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
@RequestMapping("/keyword")
@ExposesResourceFor(Discussion.class)
public class KeywordController {

  private final KeywordRepository keywordRepository;

  @Autowired
  public KeywordController(KeywordRepository keywordRepository) {
    this.keywordRepository = keywordRepository;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Keyword> post(@RequestBody Keyword keyword) {
    keywordRepository.save(keyword);
    return ResponseEntity.created(keyword.getHref()).body(keyword);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Keyword> get() {
    return keywordRepository.getAllByOrderByCreatedDesc();
  }


}
