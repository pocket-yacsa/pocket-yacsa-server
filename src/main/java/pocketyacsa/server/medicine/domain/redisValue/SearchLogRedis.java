package pocketyacsa.server.medicine.domain.redisValue;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SearchLogRedis {

  @Schema(description = "검색어", example = "타미플루")
  private String name;

  @Schema(description = "검색일자", example = "2022-10-21T23:02:13")
  private String createdAt;
}
