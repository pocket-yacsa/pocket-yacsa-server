package pocketyacsa.server.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

  private final OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService;
  private final MyAuthenticationEntryPoint authenticationEntryPoint;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/api/login", "/api/oauth2/**", "/api/error")
        .permitAll()
        .antMatchers("/api/**")
        .authenticated()
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(authenticationEntryPoint)
        .and()
        .cors()
        .and()
        .logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/api/logout"))
        .logoutSuccessUrl("/")
        .and()
        .csrf().disable()
        .oauth2Login()
        .authorizationEndpoint()
        .baseUri("/api/login/oauth2/authorize") // 로그인 페이지로 리다이렉션하는 URL 변경
        .and()
        .redirectionEndpoint()
        .baseUri("/api/login/oauth2/code/*") // 인증 코드를 받는 URL 변경
        .and()
        .defaultSuccessUrl("/camera", true)
        .authorizationEndpoint()
        .baseUri("/api/oauth2/login")
        .and()
        .userInfoEndpoint()
        .userService(oAuth2UserService);
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();

    configuration.addAllowedOrigin("http://localhost:3000");
    configuration.addAllowedOrigin("https://pocketyacsa.vercel.app");
    configuration.addAllowedOrigin("https://pocketyacsa.shop");
    configuration.addAllowedHeader("*");
    configuration.addAllowedMethod("*");
    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}