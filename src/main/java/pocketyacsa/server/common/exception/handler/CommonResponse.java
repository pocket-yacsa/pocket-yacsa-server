package pocketyacsa.server.common.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class CommonResponse {

  private String name;
  private HttpStatus httpStatus;
  private String message;

  public static CommonResponse of(String name, HttpStatus httpStatus, String message) {
    return new CommonResponse(name, httpStatus, message);
  }
}
