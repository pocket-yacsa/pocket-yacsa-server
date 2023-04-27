package pocketyacsa.server.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pocketyacsa.server.member.entity.response.MyPageRes;
import pocketyacsa.server.member.service.ResponseService;

@Tag(name = "마이페이지 데이터 조회", description = "마이페이지에 필요한 데이터들을 조회합니다.")
@RestController
@RequestMapping("/my-page")
@RequiredArgsConstructor
public class MyPageController {

  private final ResponseService responseService;

  /**
   * 마이페이지 화면에 출력할 데이터들을 반환합니다.
   *
   * @return 마이페이지에 출력할 데이터들
   */
  @Operation(summary = "마이페이지 출력 데이터 조회",
      description = "로그인한 사용자의 마이페이지에 출력할 데이터들을 조회합니다.")
  @GetMapping("/info")
  public MyPageRes getMyPage() {
    MyPageRes myPage = responseService.getMyPage();

    return myPage;
  }
}
