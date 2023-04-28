package pocketyacsa.server.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI getOpenApi() {
    Server server = new Server().url("/");

    return new OpenAPI()
        .info(getApiInfo())
        .addServersItem(server);
  }

  private Info getApiInfo() {
    return new Info()
        .title("포켓약사 API")
        .description("포켓약사 클라이언트 개발에 필요한 API를 제공합니다.")
        .version("0.0.9");
  }
}
