package pocketyacsa.server.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

  private final OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/login", "/oauth2/**", "/error")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .logoutSuccessUrl("/oauth2/logout-success")
        .and()
        .csrf().disable()
        .oauth2Login()
        .defaultSuccessUrl("/oauth2/login-success", true)
        .authorizationEndpoint()
        .baseUri("/oauth2/login")
        .and()
        .userInfoEndpoint()
        .userService(oAuth2UserService);
  }
}