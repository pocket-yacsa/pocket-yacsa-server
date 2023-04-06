package pocketyacsa.server.medicine.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pocketyacsa.server.medicine.domain.response.MedicineRes;
import pocketyacsa.server.medicine.domain.response.MedicineSearchPageRes;
import pocketyacsa.server.medicine.service.MedicineSearchService;
import pocketyacsa.server.medicine.service.MedicineService;

@RestController
@RequestMapping("/medicines")
@RequiredArgsConstructor
public class MedicineController {

  private final MedicineService medicineService;
  private final MedicineSearchService medicineSearchService;

  /**
   * 특정 id의 medicine을 반환합니다.
   *
   * @param id medicine의 id
   * @return 특정 id의 medicine
   */
  @GetMapping("/id/{id}")
  public MedicineRes getMedicineById(@PathVariable int id) {
    MedicineRes medicine = medicineService.getMedicineDtoById(id);
    return medicine;
  }

  /**
   * 특정 code의 medicine을 반환합니다.
   *
   * @param code medicine의 code
   * @return 특정 code의 medicine
   */
  @GetMapping("/code/{code}")
  public MedicineRes getMedicineByCode(@PathVariable String code) {
    MedicineRes medicine = medicineService.getMedicineDtoByCode(code);
    return medicine;
  }

  /**
   * 특정 name의 medicine 검색결과를 반환합니다. page를 넘겨줌으로써 특정 페이지의 정보로 제공됩니다.
   *
   * @param keyword 의약품 검색어
   * @param page    검색결과 페이지
   * @return 특정 page의 name으로 검색한 결과
   */
  @GetMapping("/search")
  public MedicineSearchPageRes getMedicinesByNameAndPage(@RequestParam String keyword,
      @RequestParam int page) {
    return medicineSearchService.getMedicineSearchesByNameAndPage(keyword, page);
  }

  /**
   * 특정 검색어와 연관도가 높은 검색어를 최대 10개까지 반환합니다.
   *
   * @param name 의약품 검색어
   * @return 연관도가 높은 검색어들
   */
  @GetMapping("/search/related")
  public List<String> getMedicineNamesByKeyword(@RequestParam String name) {
    return medicineSearchService.getMedicineNamesByKeyword(name);
  }
}
