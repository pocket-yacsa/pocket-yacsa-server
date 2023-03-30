package pocketyacsa.server.medicine.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pocketyacsa.server.medicine.entity.Medicine;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FavoriteDto {

  private int id;

  private int memberId;

  private Medicine medicine;
}
