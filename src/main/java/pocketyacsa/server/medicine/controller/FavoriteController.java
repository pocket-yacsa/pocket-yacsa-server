package pocketyacsa.server.medicine.controller;

import static org.springframework.http.HttpStatus.CREATED;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pocketyacsa.server.common.exception.handler.CommonResponse;
import pocketyacsa.server.medicine.entity.dto.FavoriteDto;
import pocketyacsa.server.medicine.service.FavoriteService;

@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
public class FavoriteController {

  private final FavoriteService favoriteService;

  /**
   * 특정 medicie을 즐겨찾기에 추가합니다.
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
   * 특정 id의 favorite을 반환합니다.
   *
   * @param id favorite의 id
   * @return 특정 id의 favorite
   */
  @GetMapping("/id/{id}")
  public FavoriteDto getFavoriteById(@PathVariable int id) {
    FavoriteDto favorite = favoriteService.getFavoriteDtoById(id);
    return favorite;
  }
}
