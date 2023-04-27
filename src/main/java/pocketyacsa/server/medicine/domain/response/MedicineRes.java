package pocketyacsa.server.medicine.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class MedicineRes {

  @Schema(description = "의약품의 id", defaultValue = "1")
  private int id;

  @Schema(description = "의약품의 code", defaultValue = "1")
  private String code;

  @Schema(description = "의약품의 이름", defaultValue = "타미플루")
  private String name;

  @Schema(description = "의약품의 제약회사", defaultValue = "화이자")
  private String company;

  @Schema(description = "의약품의 성분", defaultValue = " [\n"
      + "    \"구연산칼륨\",\n"
      + "    \"포도당\",\n"
      + "    \"염화나트륨\",\n"
      + "    \"구연산나트륨\"\n"
      + "  ]")
  private List<String> ingredient;

  @Schema(description = "의약품 이미지 주소",
      defaultValue = "https://kr.object.ncloudstorage.com/yacsa/medicine-image/img_not-exist.jpg")
  private String image;

  @Schema(description = "의약품의 효능", defaultValue = "감기에 좋습니다")
  private String effect;

  @Schema(description = "의약품의 용법", defaultValue = "하루 3회만 드세요")
  private String usages;

  @Schema(description = "의약품의 주의사항", defaultValue = "공복에 복용하지 마세요")
  private String precautions;
}
