package pocketyacsa.server.member.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static pocketyacsa.server.common.exception.handler.ErrorResponse.of;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pocketyacsa.server.common.exception.handler.ErrorResponse;

@Getter
@AllArgsConstructor
public enum MemberErrorResponse {

  // 404 NOT_FOUND
  MEMBER_NOT_EXIST(of("MEMBER_NOT_EXIST", NOT_FOUND, "회원이 존재하지 않습니다."));

  private ErrorResponse errorResponse;
}
