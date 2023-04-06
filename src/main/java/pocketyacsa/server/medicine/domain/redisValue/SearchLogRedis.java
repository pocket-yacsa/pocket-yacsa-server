package pocketyacsa.server.medicine.domain.redisValue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchLogRedis {

  private String name;

  private String createdAt;
}
