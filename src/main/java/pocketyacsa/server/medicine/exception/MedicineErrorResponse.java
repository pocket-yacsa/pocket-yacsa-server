package pocketyacsa.server.medicine.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static pocketyacsa.server.common.exception.handler.ErrorResponse.of;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pocketyacsa.server.common.exception.handler.ErrorResponse;

@Getter
@AllArgsConstructor
public enum MedicineErrorResponse {

  // 400 BAD_REQUEST
  PAGE_OUT_OF_RANGE(
      of("PAGE_OUT_OF_RANGE", BAD_REQUEST, "페이지 범위를 벗어납니다.")),
  KEYWORD_NOT_EXIST(
      of("KEYWORD_NOT_EXIST", BAD_REQUEST, "검색어를 입력해주세요.")),

  // 403 FORBIDDEN
  FAVORITE_NO_PERMISSION(
      of("FAVORITE_NO_PERMISSION", FORBIDDEN, "즐겨찾기에 권한이 없습니다.")),

  DETECTION_LOG_NO_PERMISSION(
      of("DETECTION_LOG_NO_PERMISSION", FORBIDDEN, "촬영기록에 권한이 없습니다.")),

  // 404 NOT_FOUND
  MEDICINE_NOT_EXIST(
      of("MEDICINE_NOT_EXIST", NOT_FOUND, "의약품이 존재하지 않습니다.")),

  FAVORITE_NOT_EXIST(
      of("FAVORITE_NOT_EXIST", NOT_FOUND, "즐겨찾기가 존재하지 않습니다.")),

  DETECTION_LOG_NOT_EXIST(
      of("DETECTION_LOG_NOT_EXIST", NOT_FOUND, "촬영기록이 존재하지 않습니다.")),

  SEARCH_RESULT_NOT_EXIST(
      of("SEARCH_RESULT_NOT_EXIST", NOT_FOUND, "검색결과가 존재하지 않습니다.")),

  // 409 CONFLICT
  FAVORITE_ALREADY_EXIST(
      of("FAVORITE_ALREADY_EXIST", CONFLICT, "이미 즐겨찾기에 추가했습니다."));

  private ErrorResponse errorResponse;
}
