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
public class FavoritePageRes {

  @Schema(description = "회원의 id", defaultValue = "1")
  private int memberId;

  @Schema(description = "전체 즐겨찾기 개수", defaultValue = "21")
  private int total;

  @Schema(description = "전체 페이지 개수", defaultValue = "4")
  private int totalPage;

  @Schema(description = "현재 페이지 번호", defaultValue = "3")
  private int page;

  @Schema(description = "마지막 페이지 여부", defaultValue = "false",
      allowableValues = {"true", "false"})
  private boolean lastPage;

  @Schema(description = "촬영기록", defaultValue = "[\n"
      + "    {\n"
      + "      \"id\": 13,\n"
      + "      \"medicineId\": 42,\n"
      + "      \"medicineName\": \"조바코정40밀리그람(심바스타틴)(수출용)\",\n"
      + "      \"medicineCompany\": \"한국유니온제약(주)\",\n"
      + "      \"medicineImage\": \"https://kr.object.ncloudstorage.com/yacsa/medicine-image/img_not-exist.jpg\",\n"
      + "      \"createdAt\": \"2023-04-02T17:25:37\"\n"
      + "    },\n"
      + "    {\n"
      + "      \"id\": 14,\n"
      + "      \"medicineId\": 425,\n"
      + "      \"medicineName\": \"로엔크림\",\n"
      + "      \"medicineCompany\": \"영진약품(주)\",\n"
      + "      \"medicineImage\": \"https://kr.object.ncloudstorage.com/yacsa/medicine-image/img_not-exist.jpg\",\n"
      + "      \"createdAt\": \"2023-04-02T17:25:44\"\n"
      + "    },\n"
      + "    {\n"
      + "      \"id\": 15,\n"
      + "      \"medicineId\": 4256,\n"
      + "      \"medicineName\": \"코니젤라틴공캡슐1호\",\n"
      + "      \"medicineCompany\": \"(주)굿윌스\",\n"
      + "      \"medicineImage\": \"https://kr.object.ncloudstorage.com/yacsa/medicine-image/img_not-exist.jpg\",\n"
      + "      \"createdAt\": \"2023-04-02T17:25:48\"\n"
      + "    }\n"
      + "  ]")
  private List<FavoriteRes> favorites;
}
