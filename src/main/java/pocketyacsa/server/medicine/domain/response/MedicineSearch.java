package pocketyacsa.server.medicine.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "medicine")
@Getter
@Builder
@EqualsAndHashCode
public class MedicineSearch {

  @Schema(description = "의약품의 id", defaultValue = "1")
  private int id;

  @Schema(description = "의약품의 이름", defaultValue = "타미플루")
  private String name;

  @Schema(description = "제약회사", defaultValue = "화이자")
  private String company;

  @Schema(description = "의약품 이미지 주소",
      defaultValue = "https://kr.object.ncloudstorage.com/yacsa/medicine-image/img_not-exist.jpg")
  private String image;
}
