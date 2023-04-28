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
public class MedicineSearchPageRes {

  @Schema(description = "전체 검색기록 개수", example = "21")
  private int total;

  @Schema(description = "전체 페이지 개수", example = "4")
  private int totalPage;

  @Schema(description = "현재 페이지 번호", example = "3")
  private int page;

  @Schema(description = "마지막 페이지 여부", example = "false",
      allowableValues = {"true", "false"})
  private boolean lastPage;

  @Schema(description = "검색결과", example = "[\n"
      + "    {\n"
      + "      \"id\": 5844,\n"
      + "      \"name\": \"타미플루캡슐30밀리그램(인산오셀타미비르)\",\n"
      + "      \"company\": \"(주)한국로슈\",\n"
      + "      \"image\": \"https://kr.object.ncloudstorage.com/yacsa/medicine-image/img_200801559.jpg\"\n"
      + "    },\n"
      + "    {\n"
      + "      \"id\": 5846,\n"
      + "      \"name\": \"타미플루캡슐45밀리그램(인산오셀타미비르)\",\n"
      + "      \"company\": \"(주)한국로슈\",\n"
      + "      \"image\": \"https://kr.object.ncloudstorage.com/yacsa/medicine-image/img_200801562.jpg\"\n"
      + "    },\n"
      + "    {\n"
      + "      \"id\": 47030,\n"
      + "      \"name\": \"타미플루캡슐75밀리그램(인산오셀타미비르)\",\n"
      + "      \"company\": \"(주)한국로슈\",\n"
      + "      \"image\": \"https://kr.object.ncloudstorage.com/yacsa/medicine-image/img_200008798.jpg\"\n"
      + "    },\n"
      + "    {\n"
      + "      \"id\": 22518,\n"
      + "      \"name\": \"타미플루현탁용분말6mg/ml(인산오셀타미비르)\",\n"
      + "      \"company\": \"(주)한국로슈\",\n"
      + "      \"image\": \"https://kr.object.ncloudstorage.com/yacsa/medicine-image/img_not-exist.jpg\"\n"
      + "    }\n"
      + "  ]")
  private List<MedicineSearch> medicineSearchList;
}
