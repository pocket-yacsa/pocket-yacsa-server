package pocketyacsa.server.medicine.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pocketyacsa.server.common.exception.handler.CommonResponse;
import pocketyacsa.server.common.utility.SortDirection;
import pocketyacsa.server.medicine.domain.response.DetectionLogPageRes;
import pocketyacsa.server.medicine.service.DetectionLogService;

@Tag(name = "의약품 촬영 기록", description = "의약품 촬영 기록 관련 api 입니다.")
@RestController
@RequestMapping("/detection-logs")
@RequiredArgsConstructor
public class DetectionLogController {

  private final DetectionLogService detectionLogService;

  /**
   * 특정 id의 detectionLog를 삭제합니다.
   *
   * @param id 삭제할 detectionLog id
   * @return detectionLog 삭제 성공 여부
   */
  @Operation(summary = "촬영기록 단일 삭제.",
      description = "특정 id의 촬영기록을 삭제합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "촬영기록 삭제 성공",
          content = @Content(schema = @Schema(
              example = "{\n"
                  + "  \"name\": \"DELETE_DETECTION_LOG_SUCCESS\",\n"
                  + "  \"httpStatus\": \"OK\",\n"
                  + "  \"message\": \"촬영기록 삭제 성공\"\n"
                  + "}"))),
      @ApiResponse(responseCode = "404 (MEMBER_NOT_EXIST)", description = "회원가입 정보 없음",
          content = @Content(schema = @Schema(
              example = "{\n"
                  + "  \"name\": \"MEMBER_NOT_EXIST\",\n"
                  + "  \"httpStatus\": \"NOT_FOUND\",\n"
                  + "  \"message\": \"회원이 존재하지 않습니다.\"\n"
                  + "}"))),
      @ApiResponse(responseCode = "404 (DETECTION_LOG_NOT_EXIST)", description = "촬영기록 없음",
          content = @Content(schema = @Schema(
              example = "{\n"
                  + "  \"name\": \"DETECTION_LOG_NOT_EXIST\",\n"
                  + "  \"httpStatus\": \"NOT_FOUND\",\n"
                  + "  \"message\": \"촬영기록이 존재하지 않습니다.\"\n"
                  + "}"))),
      @ApiResponse(responseCode = "403 (DETECTION_LOG_NO_PERMISSION)", description = "촬영기록 조회 권한 없음",
          content = @Content(schema = @Schema(
              example = "{\n"
                  + "  \"name\": \"DETECTION_LOG_NO_PERMISSION\",\n"
                  + "  \"httpStatus\": \"FORBIDDEN\",\n"
                  + "  \"message\": \"촬영기록에 권한이 없습니다.\"\n"
                  + "}")))
  })
  @DeleteMapping
  public ResponseEntity<Object> delete(@RequestParam int id) {
    detectionLogService.delete(id);
    CommonResponse response =
        new CommonResponse("DELETE_DETECTION_LOG_SUCCESS", OK, "촬영기록 삭제 성공");
    return new ResponseEntity<>(response, response.getHttpStatus());
  }

  /**
   * 로그인한 사용자의 모든 detectionLog를 삭제합니다.
   *
   * @return detectionLog 삭제 성공 여부
   */
  @Operation(summary = "촬영기록 전체 삭제",
      description = "전체 촬영기록을 삭제합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "촬영기록 삭제 성공",
          content = @Content(schema = @Schema(
              example = "{\n"
                  + "  \"name\": \"DELETE_DETECTION_LOG_SUCCESS\",\n"
                  + "  \"httpStatus\": \"OK\",\n"
                  + "  \"message\": \"촬영기록 삭제 성공\"\n"
                  + "}"))),
      @ApiResponse(responseCode = "404 (MEMBER_NOT_EXIST)", description = "회원가입 정보 없음",
          content = @Content(schema = @Schema(
              example = "{\n"
                  + "  \"name\": \"MEMBER_NOT_EXIST\",\n"
                  + "  \"httpStatus\": \"NOT_FOUND\",\n"
                  + "  \"message\": \"회원이 존재하지 않습니다.\"\n"
                  + "}"))),
      @ApiResponse(responseCode = "404 (DETECTION_LOG_NOT_EXIST)", description = "촬영기록 없음",
          content = @Content(schema = @Schema(
              example = "{\n"
                  + "  \"name\": \"DETECTION_LOG_NOT_EXIST\",\n"
                  + "  \"httpStatus\": \"NOT_FOUND\",\n"
                  + "  \"message\": \"촬영기록이 존재하지 않습니다.\"\n"
                  + "}")))
  })
  @DeleteMapping("/all")
  public ResponseEntity<Object> deleteAll() {
    detectionLogService.deleteAll();
    CommonResponse response =
        new CommonResponse("DELETE_DETECTION_LOG_SUCCESS", OK, "촬영기록 삭제 성공");
    return new ResponseEntity<>(response, response.getHttpStatus());
  }

  /**
   * 촬영기록에 출력할 detectionLog들을 페이지 단위로 반환합니다.
   *
   * @param page 페이지 수
   * @return 페이지에 존재하는 모든 detectionLogRes
   */
  @Operation(summary = "촬영기록 조회(페이징 적용)",
      description = "특정 페이지의 촬영기록을 조회합니다. 이 때 order에 \"ASCENDING\","
          + " \"DESCENDING\"을 입력하여 정렬기준을 변경할 수 있습니다. ")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "촬영기록 조회 성공"),
      @ApiResponse(responseCode = "404 (MEMBER_NOT_EXIST)", description = "회원가입 정보 없음",
          content = @Content(schema = @Schema(
              example = "{\n"
                  + "  \"name\": \"MEMBER_NOT_EXIST\",\n"
                  + "  \"httpStatus\": \"NOT_FOUND\",\n"
                  + "  \"message\": \"회원이 존재하지 않습니다.\"\n"
                  + "}"))),
      @ApiResponse(responseCode = "404 (DETECTION_LOG_NOT_EXIST)", description = "촬영기록 없음",
          content = @Content(schema = @Schema(
              example = "{\n"
                  + "  \"name\": \"DETECTION_LOG_NOT_EXIST\",\n"
                  + "  \"httpStatus\": \"NOT_FOUND\",\n"
                  + "  \"message\": \"촬영기록이 존재하지 않습니다.\"\n"
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
  public DetectionLogPageRes getDetectionLogsByPage(@RequestParam SortDirection order,
      @RequestParam int page) {
    return detectionLogService.getDetectionLogsByPageSorted(page, order);
  }
}

