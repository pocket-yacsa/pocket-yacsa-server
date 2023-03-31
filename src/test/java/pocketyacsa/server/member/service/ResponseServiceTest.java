package pocketyacsa.server.member.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pocketyacsa.server.medicine.service.DetectionLogService;
import pocketyacsa.server.medicine.service.FavoriteService;
import pocketyacsa.server.member.entity.Member;
import pocketyacsa.server.member.entity.response.MyPageRes;

@ExtendWith(MockitoExtension.class)
class ResponseServiceTest {

  @Mock
  MemberService memberService;

  @Mock
  DetectionLogService detectionLogService;

  @Mock
  FavoriteService favoriteService;

  @InjectMocks
  ResponseService responseService;

  Member member;

  @BeforeEach
  public void setUp() {
    member = Member.builder()
        .id(1)
        .name("hong")
        .email("hong@yacsa.com")
        .picture("picture-url")
        .deleted(false)
        .build();
  }

  @Test
  public void getMyPage_Success() {
    when(memberService.getLoginMember()).thenReturn(member);
    when(detectionLogService.getDetectionLogCount()).thenReturn(1);
    when(favoriteService.getFavoriteCount()).thenReturn(1);

    MyPageRes myPageRes = MyPageRes.builder()
        .memberName(member.getName())
        .detectionLogCount(1)
        .favoriteCount(1)
        .build();

    MyPageRes result = responseService.getMyPage();

    assertEquals(result, myPageRes);
  }
}