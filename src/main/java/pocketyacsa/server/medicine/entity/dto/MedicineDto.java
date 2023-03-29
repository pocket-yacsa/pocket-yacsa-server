package pocketyacsa.server.medicine.entity.dto;

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
public class MedicineDto {

  private int id;

  private String code;

  private String name;

  private String company;

  private List<String> ingredient;

  private String image;

  private String effect;

  private String usages;

  private String precautions;
}
