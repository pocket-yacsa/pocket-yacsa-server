package pocketyacsa.server.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pocketyacsa.server.member.entity.response.MyPageRes;
import pocketyacsa.server.member.service.ResponseService;

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
  @GetMapping("/info")
  public MyPageRes getMyPage() {
    MyPageRes myPage = responseService.getMyPage();

    return myPage;
  }
}
