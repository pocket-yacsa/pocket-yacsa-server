package pocketyacsa.server.member.controller;

import static org.springframework.http.HttpStatus.OK;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pocketyacsa.server.common.exception.handler.CommonResponse;
import pocketyacsa.server.member.service.MemberService;

@Tag(name = "회원정보 관리", description = "회원 정보를 관리합니다.")
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
  @Operation(summary = "회원 탈퇴",
      description = "로그인한 회원을 탈퇴시킵니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "회원 탈퇴 성공",
          content = @Content(schema = @Schema(
              example = "{\n"
                  + "  \"name\": \"DELETE_SUCCESS\",\n"
                  + "  \"httpStatus\": \"OK\",\n"
                  + "  \"message\": \"회원정보 삭제 성공\"\n"
                  + "}"))),
      @ApiResponse(responseCode = "404 (MEMBER_NOT_EXIST)", description = "회원가입 정보 없음",
          content = @Content(schema = @Schema(
              example = "{\n"
                  + "  \"name\": \"MEMBER_NOT_EXIST\",\n"
                  + "  \"httpStatus\": \"NOT_FOUND\",\n"
                  + "  \"message\": \"회원이 존재하지 않습니다.\"\n"
                  + "}")))
  })
  @DeleteMapping
  public ResponseEntity<Object> delete() {
    memberService.delete();
    CommonResponse response =
        new CommonResponse("DELETE_SUCCESS", OK, "회원정보 삭제 성공");
    return new ResponseEntity<>(response, response.getHttpStatus());
  }
}
