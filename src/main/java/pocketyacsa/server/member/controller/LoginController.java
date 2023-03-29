package pocketyacsa.server.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pocketyacsa.server.common.exception.handler.CommonResponse;

@RestController
@RequestMapping("/oauth2")
@RequiredArgsConstructor
public class LoginController {

  /**
   * 로그인 성공시 redirect되는 url입니다.
   *
   * @return 로그인 성공 메시지
   */
  @GetMapping("/login-success")
  public ResponseEntity<Object> loginSuccess() {
    CommonResponse response =
        new CommonResponse("LOGIN_SUCCESS", HttpStatus.OK, "로그인 성공");
    return new ResponseEntity<>(response, response.getHttpStatus());
  }

  /**
   * 로그아웃 성공시 redirect되는 url입니다.
   *
   * @return 로그아웃 성공 메시지
   */
  @GetMapping("/logout-success")
  public ResponseEntity<Object> logoutSuccess() {
    CommonResponse response =
        new CommonResponse("LOGOUT_SUCCESS", HttpStatus.OK, "로그아웃 성공");
    return new ResponseEntity<>(response, response.getHttpStatus());
  }
}
