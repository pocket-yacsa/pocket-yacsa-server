package pocketyacsa.server.member.controller;

import static org.springframework.http.HttpStatus.OK;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pocketyacsa.server.common.exception.handler.CommonResponse;
import pocketyacsa.server.member.service.MemberService;

@RestController
@RequestMapping("members")
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;

  /**
   * 회원탈퇴를 진행합니다.
   *
   * @return 회원탈퇴 성공 메시지
   */
  @DeleteMapping
  public ResponseEntity<Object> delete() {
    memberService.delete();
    CommonResponse response =
        new CommonResponse("DELETE_SUCCESS", OK, "회원정보 삭제 성공");
    return new ResponseEntity<>(response, response.getHttpStatus());
  }
}
