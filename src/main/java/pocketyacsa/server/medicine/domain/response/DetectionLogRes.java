package pocketyacsa.server.medicine.domain.response;

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

  private int id;

  private int medicineId;

  private String medicineName;

  private String medicineCompany;

  private String medicineImage;

  private LocalDateTime createdAt;
}
