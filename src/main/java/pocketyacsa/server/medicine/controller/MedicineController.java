package pocketyacsa.server.medicine.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pocketyacsa.server.medicine.entity.Medicine;
import pocketyacsa.server.medicine.service.MedicineService;

@RestController
@RequestMapping("/medicines")
@RequiredArgsConstructor
public class MedicineController {

  private final MedicineService medicineService;

  /**
   * 특정 id의 medicine을 반환합니다.
   *
   * @param id medicine의 id
   * @return 특정 id의 medicine
   */
  @GetMapping("/id/{id}")
  public Medicine getMedicineById(@PathVariable int id) {
    Medicine medicine = medicineService.getMedicineById(id);
    return medicine;
  }

  /**
   * 특정 code의 medicine을 반환합니다.
   *
   * @param code medicine의 code
   * @return 특정 code의 medicine
   */
  @GetMapping("/code/{code}")
  public Medicine getMedicineByCode(@PathVariable String code) {
    Medicine medicine = medicineService.getMedicineByCode(code);
    return medicine;
  }
}
