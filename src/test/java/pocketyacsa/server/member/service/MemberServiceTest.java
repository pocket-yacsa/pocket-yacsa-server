package pocketyacsa.server.member.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import pocketyacsa.server.member.entity.Member;
import pocketyacsa.server.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

  @Mock
  private MemberRepository memberRepository;

  @Mock
  private HttpSession httpSession;

  @InjectMocks
  private MemberService memberService;

  private String name, email, picture;
  private OAuth2User user;
  private Member member;


  @BeforeEach
  public void setUp() {
    name = "John Doe";
    email = "test@example.com";
    picture = "http://example.com/picture.jpg";

    Map<String, Object> attributes = new HashMap<>();
    attributes.put("name", name);
    attributes.put("email", email);
    attributes.put("picture", picture);

    user = new DefaultOAuth2User(
        AuthorityUtils.createAuthorityList("USER"),
        attributes, "email");

    member = Member.builder()
        .name((String) attributes.get("name"))
        .email((String) attributes.get("email"))
        .picture((String) attributes.get("picture"))
        .deleted(false)
        .build();
  }


  @Test
  public void signUp_Success() {
    when(memberRepository.existsMemberByEmailAndDeletedFalse(email)).thenReturn(false);

    memberService.signUp(user);

    verify(memberRepository).save(member);
  }

  @Test
  public void signUp_AlreadySignUp() {
    when(memberRepository.existsMemberByEmailAndDeletedFalse(email)).thenReturn(true);

    memberService.signUp(user);

    verify(memberRepository, never()).save(member);
  }

  @Test
  public void getEmailFromLoginMember_Success() {
    SecurityContextHolder.getContext()
        .setAuthentication(new UsernamePasswordAuthenticationToken(user, null));

    String result = memberService.getEmailFromLoginMember();

    assertEquals(email, result);
  }

  @Test
  public void getLoginMember_Success() {
    SecurityContextHolder.getContext()
        .setAuthentication(new UsernamePasswordAuthenticationToken(user, null));

    when(memberRepository.findByEmailAndDeletedFalse(email)).thenReturn(Optional.of(member));

    Member loginMember = memberService.getLoginMember();

    assertEquals(loginMember, member);
  }

  @Test
  public void delete_Success() {
    when(memberRepository.findByEmailAndDeletedFalse(email)).thenReturn(Optional.of(member));
    SecurityContextHolder.getContext()
        .setAuthentication(new UsernamePasswordAuthenticationToken(user, null));

    memberService.delete();

    verify(memberRepository).delete(member.getId());
    verify(httpSession).invalidate();
  }
}