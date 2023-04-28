package pocketyacsa.server.medicine.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
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
public class FavoriteRes {

  @Schema(description = "즐겨찾요 id", example = "1")
  private int id;

  @Schema(description = "의약품의 id", example = "1")
  private int medicineId;

  @Schema(description = "의약품 이름", example = "타미플루")
  private String medicineName;

  @Schema(description = "제약회사 이름", example = "화이자")
  private String medicineCompany;

  @Schema(description = "의약품 이미지 주소",
      example = "https://kr.object.ncloudstorage.com/yacsa/medicine-image/img_not-exist.jpg")
  private String medicineImage;

  @Schema(description = "즐겨찾기 추가 시간", example = "2023-04-02T17:25:44")
  private LocalDateTime createdAt;
}
