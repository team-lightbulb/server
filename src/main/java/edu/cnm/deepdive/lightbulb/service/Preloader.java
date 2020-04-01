package edu.cnm.deepdive.lightbulb.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.cnm.deepdive.lightbulb.model.entity.Comment;
import edu.cnm.deepdive.lightbulb.model.entity.Keyword;
import edu.cnm.deepdive.lightbulb.model.entity.User;
import edu.cnm.deepdive.lightbulb.model.repository.CommentRepository;
import edu.cnm.deepdive.lightbulb.model.repository.KeywordRepository;
import edu.cnm.deepdive.lightbulb.model.repository.UserRepository;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
@Profile("preload")
public class Preloader implements CommandLineRunner {

  private static final String KEYWORD_DATA = "preload/keywords.json";
  private static final String USER_DATA = "preload/users.json";
  private static final String COMMENT_DATA = "preload/comments.json";

  private final CommentRepository commentRepository;
  private final UserRepository userRepository;
  private final KeywordRepository keywordRepository;

  public Preloader(CommentRepository commentRepository,
      UserRepository userRepository,
      KeywordRepository keywordRepository) {
    this.commentRepository = commentRepository;
    this.userRepository = userRepository;
    this.keywordRepository = keywordRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    loadKeywords();
    loadUsers();
    loadComments();
  }

  private void loadKeywords() throws IOException {
    ClassPathResource resource = new ClassPathResource(KEYWORD_DATA);
    try (InputStream input = resource.getInputStream()) {
      ObjectMapper mapper = new ObjectMapper();
      keywordRepository.saveAll(Arrays.asList(mapper.readValue(input, Keyword[].class)));
    }
  }

  private void loadUsers() throws IOException {
    ClassPathResource resource = new ClassPathResource(USER_DATA);
    try (InputStream input = resource.getInputStream()) {
      ObjectMapper mapper = new ObjectMapper();
      userRepository.saveAll(Arrays.asList(mapper.readValue(input, User[].class)));
    }
  }

  private void loadComments() throws IOException {
    ClassPathResource resource = new ClassPathResource(COMMENT_DATA);
    try (InputStream input = resource.getInputStream()) {
      ObjectMapper mapper = new ObjectMapper();
      Comment[] comments = mapper.readValue(input, Comment[].class);
      for (Comment comment : comments) {
        resolveReferences(comment);
      }
      commentRepository.saveAll(Arrays.asList(comments));
    }
  }

  private void resolveReferences(Comment comment) {
    comment.setUser(userRepository.findFirstByOauthKey(comment.getUser().getOauthKey()));
    List<Keyword> keywords = new LinkedList<>();
    for (Keyword keyword : comment.getKeywords()) {
      keyword = keywordRepository.findFirstByName(keyword.getName()).orElse(keyword);
      keyword.getComments().add(comment);
      keywords.add(keyword);
    }
    comment.getKeywords().clear();
    comment.getKeywords().addAll(keywords);
    for (Comment response : comment.getResponses()) {
      response.setReference(comment);
      resolveReferences(response);
    }
  }

}
