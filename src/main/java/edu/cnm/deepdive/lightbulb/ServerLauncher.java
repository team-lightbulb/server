package edu.cnm.deepdive.lightbulb;

import org.springframework.boot.builder.SpringApplicationBuilder;

public class ServerLauncher {

  public static void main(String[] args) {
    new SpringApplicationBuilder()
        .sources(LightbulbApplication.class)
        .profiles("server")
        .run(args);
  }
}
