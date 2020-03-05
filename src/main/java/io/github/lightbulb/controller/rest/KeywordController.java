package io.github.lightbulb.controller.rest;

import io.github.lightbulb.controller.exception.SearchTermTooShortException;
import io.github.lightbulb.model.entity.Comment;
import io.github.lightbulb.model.entity.Keyword;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/keywords")
@ExposesResourceFor(Comment.class)
public class KeywordController {

  private final KeywordRepository keywordRepository;
  private final CommentRepository commentRepository;

  @Autowired
  public KeywordController(KeywordRepository keywordRepository,
      CommentRepository commentRepository) {
    this.keywordRepository = keywordRepository;
    this.commentRepository = commentRepository;
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

  @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Keyword> search(@RequestParam("q") String fragment) {
    if (fragment.length() < 3) {
      throw new SearchTermTooShortException();
    }
    return keywordRepository.getAllByNameContainsOrderByNameAsc(fragment);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Keyword get(@PathVariable UUID id) {
    return keywordRepository.findOrFail(id);
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    commentRepository.findById(id).ifPresent(commentRepository::delete);
  }

}
