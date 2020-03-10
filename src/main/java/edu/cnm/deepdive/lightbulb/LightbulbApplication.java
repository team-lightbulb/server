package edu.cnm.deepdive.lightbulb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;

@EnableHypermediaSupport(type = HypermediaType.HAL)
@SpringBootApplication
public class LightbulbApplication {


  /***
   *
   * @param args
   */
  public static void main(String[] args) {
    SpringApplication.run(LightbulbApplication.class, args);
  }

}
