package pocketyacsa.server.medicine.controller;

import static org.springframework.http.HttpStatus.OK;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pocketyacsa.server.common.exception.handler.CommonResponse;
import pocketyacsa.server.medicine.domain.redisValue.SearchLogRedis;
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
   * 특정 name의 medicine 검색결과의 첫번째 페이지를 반환합니다. 이 때 검색기록에 추가됩니다.
   *
   * @param name 의약품 검색어
   * @return name으로 검색한 첫번째 결과
   */
  @GetMapping("/search")
  public MedicineSearchPageRes getMedicineSearchAtFirst(@RequestParam String name) {
    return medicineSearchService.getMedicineSearchAtFist(name);
  }

  /**
   * 특정 name의 medicine 검색결과를 반환합니다. page를 넘겨줌으로써 특정 페이지의 정보로 제공됩니다.
   *
   * @param name 의약품 검색어
   * @param page 검색결과 페이지
   * @return 특정 page의 name으로 검색한 결과
   */
  @GetMapping("/search/page")
  public MedicineSearchPageRes getMedicinesByNameAndPage(@RequestParam String name,
      @RequestParam int page) {
    return medicineSearchService.getMedicineSearchesByNameAndPage(name, page);
  }

  /**
   * 특정 검색어와 연관도가 높은 검색어를 최대 PAGE_SIZE만큼 반환합니다.
   *
   * @param name 의약품 검색어
   * @return 연관도가 높은 검색어들
   */
  @GetMapping("/search/related")
  public List<String> getMedicineNamesByKeyword(@RequestParam String name) {
    return medicineSearchService.getMedicineNamesByKeyword(name);
  }

  /**
   * 최근 검색기록을 RECENT_KEWORD_SIZE만큼 반환합니다.
   *
   * @return 최근 검색기록들
   */
  @GetMapping("/search/logs")
  public List<SearchLogRedis> getMedicineSearchLogs() {
    return medicineSearchService.getRecentSearchLogs();
  }

  /**
   * 특정 검색기록을 삭제합니다.
   *
   * @param searchLogRedis 검색기록 정보
   * @return 검색기록 삭제 여부
   */
  @DeleteMapping("/search/logs")
  public ResponseEntity<Object> deleteLog(@RequestBody SearchLogRedis searchLogRedis) {
    medicineSearchService.deleteRecentSearchLog(searchLogRedis);
    CommonResponse response =
        new CommonResponse("DELETE_SEARCH_LOG_SUCCESS", OK, "검색기록 삭제 성공");
    return new ResponseEntity<>(response, response.getHttpStatus());
  }

  /**
   * 전체 검색기록을 삭제합니다.
   *
   * @return 검색기록 삭제 여부
   */
  @GetMapping("/search/logs/all")
  public ResponseEntity<Object> deleteLogs() {
    medicineSearchService.deleteRecentSearchLogs();
    CommonResponse response =
        new CommonResponse("DELETE_SEARCH_LOG_SUCCESS", OK, "검색기록 삭제 성공");
    return new ResponseEntity<>(response, response.getHttpStatus());
  }
}
