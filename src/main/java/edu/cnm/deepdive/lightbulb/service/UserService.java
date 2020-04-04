package edu.cnm.deepdive.lightbulb.service;

import edu.cnm.deepdive.lightbulb.model.entity.User;
import edu.cnm.deepdive.lightbulb.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {


  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public synchronized User readOrCreateOne(String oauthKey, String displayName, String email) {
    return userRepository.findFirstByOauthKey(oauthKey)
        .orElseGet(() -> {
          User user = new User();
          user.setOauthKey(oauthKey);
          user.setDisplayName(displayName);
          user.setEmail(email);
          return userRepository.save(user);
        });
  }

}
