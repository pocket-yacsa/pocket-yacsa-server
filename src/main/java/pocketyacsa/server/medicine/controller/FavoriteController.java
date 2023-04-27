package pocketyacsa.server.medicine.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pocketyacsa.server.common.exception.handler.CommonResponse;
import pocketyacsa.server.common.utility.SortDirection;
import pocketyacsa.server.medicine.domain.response.FavoritePageRes;
import pocketyacsa.server.medicine.service.FavoriteService;
@Tag(name = "의약품 즐겨찾기", description = "의약품 즐겨찾기 관련 api 입니다.")
@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
public class FavoriteController {

  private final FavoriteService favoriteService;

  /**
   * 특정 medicine을 즐겨찾기에 추가합니다.
   *
   * @param medicineId 즐겨찾기에 추가할 medicine의 id
   * @return 즐겨찾기 추가 성공여부 메시지
   */
  @Operation(summary = "특정 의약품을 즐겨찾기에 추가",
      description = "특정 의약품의 id를 이용하여 즐겨찾기에 의약품을 추가합니다.")
  @PostMapping
  public ResponseEntity<Object> save(@RequestParam int medicineId) {
    favoriteService.save(medicineId);
    CommonResponse response =
        new CommonResponse("SAVE_FAVORITE_SUCCESS", CREATED, "즐겨찾기 추가 성공");
    return new ResponseEntity<>(response, response.getHttpStatus());
  }

  /**
   * 특정 id의 favorite을 삭제합니다.
   *
   * @param favoriteId 삭제할 favorite의 id
   * @return favorite 삭제 성공 여부
   */
  @Operation(summary = "특정 의약품을 즐겨찾기에서 삭제",
      description = "특정 id의 의약품을 즐겨찾기에서 삭제합니다.")
  @DeleteMapping
  public ResponseEntity<Object> delete(@RequestParam int favoriteId) {
    favoriteService.delete(favoriteId);
    CommonResponse response =
        new CommonResponse("DELETE_FAVORITE_SUCCESS", OK, "즐겨찾기 삭제 성공");
    return new ResponseEntity<>(response, response.getHttpStatus());
  }

  /**
   * 로그인한 사용자의 모든 favorite을 삭제합니다.
   *
   * @return favorite 삭제 성공 여부
   */
  @Operation(summary = "즐겨찾기 전체 삭제",
      description = "로그인한 사용자의 즐겨찾기를 모두 삭제합니다.")
  @DeleteMapping("/all")
  public ResponseEntity<Object> deleteAll() {
    favoriteService.deleteAll();
    CommonResponse response =
        new CommonResponse("DELETE_FAVORITE_SUCCESS", OK, "즐겨찾기 삭제 성공");
    return new ResponseEntity<>(response, response.getHttpStatus());
  }

  /**
   * 내 서랍에 출력할 favorite들을 페이지 단위로 최신순으로 반환합니다.
   *
   * @param page 페이지 수
   * @return 페이지에 존재하는 모든 favoriteRes
   */
  @Operation(summary = "즐겨찾기 조회(페이징 적용)",
      description = "특정 페이지의 즐겨찾기를 조회합니다. 이 때 order에 \"ASCENDING\","
          + " \"DESCENDING\"을 입력하여 정렬기준을 변경할 수 있습니다. ")
  @GetMapping
  public FavoritePageRes getFavoritesByPage(@RequestParam SortDirection order,
      @RequestParam int page) {
    return favoriteService.getFavoritesByPageSorted(page, order);
  }
}
