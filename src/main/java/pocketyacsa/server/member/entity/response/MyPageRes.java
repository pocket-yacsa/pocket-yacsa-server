package pocketyacsa.server.member.entity.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class MyPageRes {

  @Schema(description = "회원 이름", example = "Hong GilDong")
  private String memberName;

  @Schema(description = "촬영기록 개수", example = "15")
  private int detectionLogCount;

  @Schema(description = "즐겨찾기 개수", example = "42")
  private int favoriteCount;
}
