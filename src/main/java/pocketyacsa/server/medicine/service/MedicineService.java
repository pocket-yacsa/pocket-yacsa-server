package pocketyacsa.server.medicine.service;

import static pocketyacsa.server.medicine.exception.MedicineErrorResponse.MEDICINE_NOT_EXIST;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pocketyacsa.server.common.exception.BadRequestException;
import pocketyacsa.server.medicine.entity.Medicine;
import pocketyacsa.server.medicine.repository.MedicineRepository;

@Service
@RequiredArgsConstructor
public class MedicineService {

  private final MedicineRepository repository;

  /**
   * 특정 id의 medicine을 반환합니다.
   *
   * @param id medicine의 id
   * @return 특정 id의 medicine
   */
  public Medicine getMedicineById(int id) {
    Optional<Medicine> medicine = repository.findById(id);
    if (medicine.isEmpty()) {
      throw new BadRequestException(MEDICINE_NOT_EXIST.getErrorResponse());
    }

    return medicine.get();
  }

  /**
   * 특정 code의 medicine을 반환합니다.
   *
   * @param code medicine의 code
   * @return 특정 code의 medicine
   */
  public Medicine getMedicineByCode(String code) {
    Optional<Medicine> medicine = repository.findByCode(code);
    if (medicine.isEmpty()) {
      throw new BadRequestException(MEDICINE_NOT_EXIST.getErrorResponse());
    }

    return medicine.get();
  }

}
