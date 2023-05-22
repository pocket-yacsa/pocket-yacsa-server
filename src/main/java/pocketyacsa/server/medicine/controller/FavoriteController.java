package pocketyacsa.server.medicine.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "의약품 즐겨찾기 추가 성공",
          content = @Content(schema = @Schema(
              example = "{\n"
                  + "  \"name\": \"SAVE_FAVORITE_SUCCESS\",\n"
                  + "  \"httpStatus\": \"CREATED\",\n"
                  + "  \"message\": \"즐겨찾기 추가 성공\"\n"
                  + "}"))),
      @ApiResponse(responseCode = "404 (MEMBER_NOT_EXIST)", description = "회원가입 정보 없음",
          content = @Content(schema = @Schema(
              example = "{\n"
                  + "  \"name\": \"MEMBER_NOT_EXIST\",\n"
                  + "  \"httpStatus\": \"NOT_FOUND\",\n"
                  + "  \"message\": \"회원이 존재하지 않습니다.\"\n"
                  + "}"))),
      @ApiResponse(responseCode = "404 (MEDICINE_NOT_EXIST)", description = "의약품 정보 없음",
          content = @Content(schema = @Schema(
              example = "{\n"
                  + "  \"name\": \"MEDICINE_NOT_EXIST\",\n"
                  + "  \"httpStatus\": \"NOT_FOUND\",\n"
                  + "  \"message\": \"의약품이 존재하지 않습니다.\"\n"
                  + "}"))),
      @ApiResponse(responseCode = "409 (FAVORITE_ALREADY_EXIST)", description = "즐겨찾기 정보 없음",
          content = @Content(schema = @Schema(
              example = "{\n"
                  + "  \"name\": \"FAVORITE_ALREADY_EXIST\",\n"
                  + "  \"httpStatus\": \"CONFLICT\",\n"
                  + "  \"message\": \"이미 즐겨찾기에 추가했습니다.\"\n"
                  + "}")))
  })
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
   * @param medicineId 즐겨찾기에서 삭제할 medicine의 id
   * @return favorite 삭제 성공 여부
   */
  @Operation(summary = "특정 의약품을 즐겨찾기에서 삭제",
      description = "특정 id의 의약품을 즐겨찾기에서 삭제합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "의약품 즐겨찾기 삭제 성공",
          content = @Content(schema = @Schema(
              example = "{\n"
                  + "  \"name\": \"DELETE_FAVORITE_SUCCESS\",\n"
                  + "  \"httpStatus\": \"OK\",\n"
                  + "  \"message\": \"즐겨찾기 삭제 성공\"\n"
                  + "}"))),
      @ApiResponse(responseCode = "404 (MEMBER_NOT_EXIST)", description = "회원가입 정보 없음",
          content = @Content(schema = @Schema(
              example = "{\n"
                  + "  \"name\": \"MEMBER_NOT_EXIST\",\n"
                  + "  \"httpStatus\": \"NOT_FOUND\",\n"
                  + "  \"message\": \"회원이 존재하지 않습니다.\"\n"
                  + "}"))),
      @ApiResponse(responseCode = "404 (FAVORITE_NOT_EXIST)", description = "즐겨찾기 정보 없음",
          content = @Content(schema = @Schema(
              example = "{\n"
                  + "  \"name\": \"FAVORITE_NOT_EXIST\",\n"
                  + "  \"httpStatus\": \"NOT_FOUND\",\n"
                  + "  \"message\": \"즐겨찾기가 존재하지 않습니다.\"\n"
                  + "}"))),
      @ApiResponse(responseCode = "403 (FAVORITE_NO_PERMISSION)", description = "즐겨찾기 접근권한 없음",
          content = @Content(schema = @Schema(
              example = "{\n"
                  + "  \"name\": \"FAVORITE_NO_PERMISSION\",\n"
                  + "  \"httpStatus\": \"FORBIDDEN\",\n"
                  + "  \"message\": \"즐겨찾기에 권한이 없습니다.\"\n"
                  + "}")))
  })
  @DeleteMapping
  public ResponseEntity<Object> delete(@RequestParam int medicineId) {
    favoriteService.delete(medicineId);
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
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "의약품 즐겨찾기 삭제 성공",
          content = @Content(schema = @Schema(
              example = "{\n"
                  + "  \"name\": \"DELETE_FAVORITE_SUCCESS\",\n"
                  + "  \"httpStatus\": \"OK\",\n"
                  + "  \"message\": \"즐겨찾기 삭제 성공\"\n"
                  + "}"))),
      @ApiResponse(responseCode = "404 (MEMBER_NOT_EXIST)", description = "회원가입 정보 없음",
          content = @Content(schema = @Schema(
              example = "{\n"
                  + "  \"name\": \"MEMBER_NOT_EXIST\",\n"
                  + "  \"httpStatus\": \"NOT_FOUND\",\n"
                  + "  \"message\": \"회원이 존재하지 않습니다.\"\n"
                  + "}"))),
      @ApiResponse(responseCode = "404 (FAVORITE_NOT_EXIST)", description = "즐겨찾기 정보 없음",
          content = @Content(schema = @Schema(
              example = "{\n"
                  + "  \"name\": \"FAVORITE_NOT_EXIST\",\n"
                  + "  \"httpStatus\": \"NOT_FOUND\",\n"
                  + "  \"message\": \"즐겨찾기가 존재하지 않습니다.\"\n"
                  + "}")))
  })
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
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "의약품 즐겨찾기 조회 성공"),
      @ApiResponse(responseCode = "404 (MEMBER_NOT_EXIST)", description = "회원가입 정보 없음",
          content = @Content(schema = @Schema(
              example = "{\n"
                  + "  \"name\": \"MEMBER_NOT_EXIST\",\n"
                  + "  \"httpStatus\": \"NOT_FOUND\",\n"
                  + "  \"message\": \"회원이 존재하지 않습니다.\"\n"
                  + "}"))),
      @ApiResponse(responseCode = "404 (FAVORITE_NOT_EXIST)", description = "즐겨찾기 정보 없음",
          content = @Content(schema = @Schema(
              example = "{\n"
                  + "  \"name\": \"FAVORITE_NOT_EXIST\",\n"
                  + "  \"httpStatus\": \"NOT_FOUND\",\n"
                  + "  \"message\": \"즐겨찾기가 존재하지 않습니다.\"\n"
                  + "}"))),
      @ApiResponse(responseCode = "400 (PAGE_OUT_OF_RANGE)", description = "페이지 범위를 벗어나서 조회",
          content = @Content(schema = @Schema(
              example = "{\n"
                  + "  \"name\": \"PAGE_OUT_OF_RANGE\",\n"
                  + "  \"httpStatus\": \"BAD_REQUEST\",\n"
                  + "  \"message\": \"페이지 범위를 벗어납니다.\"\n"
                  + "}")))
  })
  @GetMapping
  public FavoritePageRes getFavoritesByPage(@RequestParam SortDirection order,
      @RequestParam int page) {
    return favoriteService.getFavoritesByPageSorted(page, order);
  }
}
