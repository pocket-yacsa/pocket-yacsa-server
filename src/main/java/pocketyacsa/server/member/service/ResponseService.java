package pocketyacsa.server.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pocketyacsa.server.medicine.service.DetectionLogService;
import pocketyacsa.server.medicine.service.FavoriteService;
import pocketyacsa.server.member.entity.Member;
import pocketyacsa.server.member.entity.response.MyPageRes;

@Service
@RequiredArgsConstructor
public class ResponseService {

  private final MemberService memberService;
  private final DetectionLogService detectionLogService;
  private final FavoriteService favoriteService;

  /**
   * 마이페이지 화면에 출력할 데이터들을 반환합니다.
   *
   * @return 마이페이지에 출력할 데이터들
   */
  public MyPageRes getMyPage() {
    Member loginMember = memberService.getLoginMember();
    String name = loginMember.getName();
    int detectionLogCount = detectionLogService.getDetectionLogCount();
    int favoriteCount = favoriteService.getFavoriteCount();

    MyPageRes myPageRes = MyPageRes.builder()
        .memberName(name)
        .detectionLogCount(detectionLogCount)
        .favoriteCount(favoriteCount)
        .build();

    return myPageRes;
  }
}
