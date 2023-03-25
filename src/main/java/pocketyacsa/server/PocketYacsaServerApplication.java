package pocketyacsa.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession
@EnableJpaAuditing
@SpringBootApplication
public class PocketYacsaServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(PocketYacsaServerApplication.class, args);
  }

}
