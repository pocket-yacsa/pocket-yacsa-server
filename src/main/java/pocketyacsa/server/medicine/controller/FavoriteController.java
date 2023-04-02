package pocketyacsa.server.medicine.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

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
  @GetMapping
  public FavoritePageRes getFavoritesByPage(@RequestParam SortDirection order,
      @RequestParam int page) {
    return favoriteService.getFavoritesByPageSorted(page, order);
  }
}
