package pocketyacsa.server.medicine.domain.response;

import java.util.List;
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
public class FavoritePageRes {

  private int memberId;

  private int total;

  private int totalPage;

  private int page;

  private boolean lastPage;

  private List<FavoriteRes> favorites;
}
