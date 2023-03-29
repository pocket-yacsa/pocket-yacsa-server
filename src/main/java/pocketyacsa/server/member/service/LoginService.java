package pocketyacsa.server.member.service;


import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService implements OAuth2UserService {

  private final MemberService memberService;
  private final HttpSession httpSession;

  /**
   * OAuth2 로그인을 한 사용자가 회원가입이 되어있지 않으면 회원가입을 진행합니다.
   *
   * @param userRequest OAuth2 로그인 요청정보
   * @return OAuth2User
   */
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) {
    DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
    OAuth2User oAuth2User = delegate.loadUser(userRequest);

    memberService.signUp(oAuth2User);
    return oAuth2User;
  }
}