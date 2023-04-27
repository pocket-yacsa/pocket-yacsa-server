package pocketyacsa.server.medicine.controller;

import static org.springframework.http.HttpStatus.OK;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pocketyacsa.server.common.exception.handler.CommonResponse;
import pocketyacsa.server.medicine.domain.response.MedicineRes;
import pocketyacsa.server.medicine.domain.response.MedicineSearchPageRes;
import pocketyacsa.server.medicine.service.MedicineSearchService;
import pocketyacsa.server.medicine.service.MedicineService;

@Tag(name = "의약품", description = "의약품 관련 api 입니다.")
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
  @Operation(summary = "DB의 id를 이용하여 의약품 정보 조회",
      description = "RDB의 primary key인 id를 통해 의약품 정보를 조회합니다.")
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
  @Operation(summary = "의약품 code를 이용하여 의약품 정보 조회",
      description = "의약품 고유값인 code를 이용하여 의약품 정보를 조회합니다.")
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
  @Operation(summary = "의약품 검색",
      description = "의약품 검색을 할 때 첫번째 페이지를 출력합니다. 이 때 검색기록에 추가됩니다.")
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
  @Operation(summary = "의약품 검색 후 다른 결과 페이지 조회",
      description = "의약품 검색을 통해 첫번째 페이지를 반환 받은 후 추가적인 검색기록을 확인하기 위해서 사용합니다. "
          + "이 때는 검색기록에 추가되지 않습니다.")
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
  @Operation(summary = "의약품 연관검색어 조회",
      description = "의약품 검색어와 연관도가 높은 검색어를 최대 PAGE_SIZE만큼 반환합니다.")
  @GetMapping("/search/related")
  public List<String> getMedicineNamesByKeyword(@RequestParam String name) {
    return medicineSearchService.getMedicineNamesByKeyword(name);
  }

  /**
   * 최근 검색기록을 RECENT_KEYWORD_SIZE만큼 반환합니다.
   *
   * @return 최근 검색기록들
   */
  @Operation(summary = "의약품 최근 검색기록 조회",
      description = "의약품 최근 검색기록을 RECENT_KEYWORD_SIZE만큼 반환합니다.")
  @GetMapping("/search/logs")
  public List<String> getMedicineSearchLogs() {
    return medicineSearchService.getRecentSearchLogs();
  }

  /**
   * 특정 검색기록을 삭제합니다.
   *
   * @param index 검색기록내의 index
   * @return 검색기록 삭제 여부
   */
  @Operation(summary = "특정 의약품 검색기록 삭제",
      description = "list에 저장되어 있는 검색기록중 특정 index에 존재하는 검색기록을 삭제합니다.")
  @DeleteMapping("/search/logs")
  public ResponseEntity<Object> deleteLog(@RequestParam int index) {
    medicineSearchService.deleteRecentSearchLog(index);
    CommonResponse response = new CommonResponse("DELETE_SEARCH_LOG_SUCCESS", OK, "검색기록 삭제 성공");
    return new ResponseEntity<>(response, response.getHttpStatus());
  }

  /**
   * 전체 검색기록을 삭제합니다.
   *
   * @return 검색기록 삭제 여부
   */
  @Operation(summary = "전체 의약품 검색기록 삭제",
      description = "전체 의약품 검색기록을 삭제합니다.")
  @DeleteMapping("/search/logs/all")
  public ResponseEntity<Object> deleteLogs() {
    medicineSearchService.deleteRecentSearchLogs();
    CommonResponse response = new CommonResponse("DELETE_SEARCH_LOG_SUCCESS", OK, "검색기록 삭제 성공");
    return new ResponseEntity<>(response, response.getHttpStatus());
  }
}
