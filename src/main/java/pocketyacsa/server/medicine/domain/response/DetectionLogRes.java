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
public class DetectionLogRes {

  @Schema(description = "촬영기록 id", defaultValue = "1")
  private int id;

  @Schema(description = "의약품의 id", defaultValue = "1")
  private int medicineId;

  @Schema(description = "의약품 이름", defaultValue = "타미플루")
  private String medicineName;

  @Schema(description = "제약회사 이름", defaultValue = "화이자")
  private String medicineCompany;

  @Schema(description = "의약품 이미지 주소",
      defaultValue = "https://kr.object.ncloudstorage.com/yacsa/medicine-image/img_not-exist.jpg")
  private String medicineImage;

  @Schema(description = "즐겨찾기 추가 시간", defaultValue = "2023-04-02T17:25:44")
  private LocalDateTime createdAt;
}
