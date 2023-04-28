package pocketyacsa.server.medicine.service;

import static pocketyacsa.server.medicine.exception.MedicineErrorResponse.MEDICINE_NOT_EXIST;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pocketyacsa.server.common.exception.BadRequestException;
import pocketyacsa.server.medicine.domain.entity.Medicine;
import pocketyacsa.server.medicine.domain.response.MedicineRes;
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

  /**
   * '/'로 구분되어 있는 ingredient 문자열을 List 형태로 성분별로 나누어 저장합니다.
   *
   * @param ingredient '/'로 구분되어 있는 ingredient 문자열
   * @return ingredient를 종류별로 저장하는 List
   */
  public List<String> getIngredientList(String ingredient) {
    List<String> list = Arrays.asList(ingredient.split("\\|"));
    return list;
  }

  /**
   * 특정 id의 클라이언트에게 전달할 Medicine 정보를 반환합니다.
   *
   * @param id medicine의 id
   * @return
   */
  public MedicineRes getMedicineResById(int id) {
    Medicine medicine = getMedicineById(id);

    MedicineRes medicineRes = MedicineRes.builder()
        .id(medicine.getId())
        .code(medicine.getCode())
        .name(medicine.getName())
        .company(medicine.getCompany())
        .ingredient(getIngredientList(medicine.getIngredient()))
        .image(medicine.getImage())
        .effect(medicine.getEffect())
        .usages(medicine.getUsages())
        .precautions(medicine.getPrecautions())
        .build();

    return medicineRes;
  }

  /**
   * 특정 id의 클라이언트에게 전달할 Medicine 정보를 반환합니다.
   *
   * @param code medicine의 code
   * @return
   */
  public MedicineRes getMedicineResByCode(String code) {
    Medicine medicine = getMedicineByCode(code);

    MedicineRes medicineRes = MedicineRes.builder()
        .id(medicine.getId())
        .code(medicine.getCode())
        .name(medicine.getName())
        .company(medicine.getCompany())
        .ingredient(getIngredientList(medicine.getIngredient()))
        .image(medicine.getImage())
        .effect(medicine.getEffect())
        .usages(medicine.getUsages())
        .precautions(medicine.getPrecautions())
        .build();

    return medicineRes;
  }
}
