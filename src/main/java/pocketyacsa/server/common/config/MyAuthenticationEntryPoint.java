package pocketyacsa.server.common.config;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException {

    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    Map<String, Object> data = new HashMap<>();
    data.put("name", "MEMBER_NOT_LOGIN");
    data.put("httpStatus", "UNAUTHORIZED");
    data.put("message", "로그인이 되어있지 않습니다.");

    String json = new Gson().toJson(data);

    response.getWriter().write(json);
  }
}
