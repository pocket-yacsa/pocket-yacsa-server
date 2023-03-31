package pocketyacsa.server.member.entity.response;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class MyPageRes {

  private String memberName;

  private int detectionLogCount;

  private int favoriteCount;
}
