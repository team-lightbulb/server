package edu.cnm.deepdive.lightbulb.controller.rest;

import edu.cnm.deepdive.lightbulb.controller.exception.SearchTermTooShortException;
import edu.cnm.deepdive.lightbulb.model.entity.Comment;
import edu.cnm.deepdive.lightbulb.model.entity.Keyword;
import edu.cnm.deepdive.lightbulb.model.repository.CommentRepository;
import edu.cnm.deepdive.lightbulb.model.repository.KeywordRepository;
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


/***
 * This class controls the keyword repository.
 */
@RestController
@RequestMapping("/keywords")
@ExposesResourceFor(Keyword.class)
public class KeywordController {

  private final KeywordRepository keywordRepository;
  private final CommentRepository commentRepository;

  /***
   * This method links keyword repository to comments repository.
   * @param keywordRepository
   * @param commentRepository
   */
  @Autowired
  public KeywordController(KeywordRepository keywordRepository,
      CommentRepository commentRepository) {
    this.keywordRepository = keywordRepository;
    this.commentRepository = commentRepository;
  }


  /***
   * This method returns the keyword repository.
   * @param keyword
   * @return
   */
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Keyword> post(@RequestBody Keyword keyword) {
    keywordRepository.save(keyword);
    return ResponseEntity.created(keyword.getHref()).body(keyword);
  }


  /***
   * This method returns keyword repository by descending order.
   * @return
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Keyword> get() {
    return keywordRepository.getAllByOrderByCreatedDesc();
  }


  /***
   * This method throws an exception and also returns keyword repository in ascending order.
   * @param fragment
   * @return
   */
  @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Keyword> search(@RequestParam("q") String fragment) {
    if (fragment.length() < 3) {
      throw new SearchTermTooShortException();
    }
    return keywordRepository.getAllByNameContainsOrderByNameAsc(fragment);
  }


  /***
   * This method returns keyword repository.
   * @param id
   * @return
   */
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Keyword get(@PathVariable UUID id) {
    return keywordRepository.findOrFail(id);
  }


  /***
   * This method deletes the keyword repository.
   * @param id
   */
  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    commentRepository.findById(id).ifPresent(commentRepository::delete);
  }

}
