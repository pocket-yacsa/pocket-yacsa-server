package pocketyacsa.server.member.service;

import static pocketyacsa.server.member.exception.MemberErrorResponse.MEMBER_NOT_EXIST;

import java.util.Optional;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pocketyacsa.server.common.exception.BadRequestException;
import pocketyacsa.server.member.entity.Member;
import pocketyacsa.server.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository repository;
  private final HttpSession httpSession;

  /**
   * 소셜로그인 진행 후 기존에 가입정보가 없으면 회원가입을 진행합니다.
   *
   * @param user 로그인을 시도한 OAuth2User 정보
   */
  public void signUp(OAuth2User user) {
    boolean isExist =
        repository.existsMemberByEmailAndDeletedFalse(user.getAttribute("email"));

    if (!isExist) {
      Member member = Member.builder().name(user.getAttribute("name"))
          .email(user.getAttribute("email"))
          .picture(user.getAttribute("picture"))
          .deleted(false)
          .build();

      repository.save(member);
    }
  }

  /**
   * 현재 로그인한 사용자의 이메일을 반환합니다.
   *
   * @return 로그인한 사용자의 이메일
   */
  public String getEmailFromLoginMember() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
    return (String) oAuth2User.getAttribute("email");
  }

  /**
   * 현재 로그인한 사용자의 정보를 반환합니다.
   *
   * @return 로그인한 사용자의 정보
   */
  public Member getLoginMember() {
    String email = getEmailFromLoginMember();
    Optional<Member> member = repository.findByEmailAndDeletedFalse(email);

    if (member.isEmpty()) {
      throw new BadRequestException(MEMBER_NOT_EXIST.getErrorResponse());
    }
    return member.get();
  }

  /**
   * 현재 로그인한 사용자의 정보를 삭제합니다.
   */
  @Transactional
  public void delete() {
    Member member = getLoginMember();
    repository.delete(member.getId());
    httpSession.invalidate();
  }
}
