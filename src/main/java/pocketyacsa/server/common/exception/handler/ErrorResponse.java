package pocketyacsa.server.common.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ErrorResponse {

  private String name;
  private HttpStatus httpStatus;
  private String message;

  public static ErrorResponse of(String name, HttpStatus httpStatus, String message) {
    return new ErrorResponse(name, httpStatus, message);
  }
}
