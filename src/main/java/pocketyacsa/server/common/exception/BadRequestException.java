package pocketyacsa.server.common.exception;

import lombok.Getter;
import pocketyacsa.server.common.exception.handler.ErrorResponse;

@Getter
public class BadRequestException extends RuntimeException {

  private ErrorResponse errorResponse;

  public BadRequestException(ErrorResponse errorResponse) {
    this.errorResponse = ErrorResponse.
        of(errorResponse.getName(), errorResponse.getHttpStatus(), errorResponse.getMessage());
  }
}
