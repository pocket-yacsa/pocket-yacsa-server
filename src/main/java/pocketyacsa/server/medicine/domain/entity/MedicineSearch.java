package pocketyacsa.server.medicine.domain.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "medicine")
@Getter
@Builder
@EqualsAndHashCode
public class MedicineSearch {

  private int id;

  private String name;

  private String company;

  private String image;
}
