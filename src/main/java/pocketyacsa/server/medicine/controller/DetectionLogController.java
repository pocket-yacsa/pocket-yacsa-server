package pocketyacsa.server.medicine.controller;

import static org.springframework.http.HttpStatus.OK;

import io.swagger.v3.oas.annotations.Operation;
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
  @GetMapping
  public DetectionLogPageRes getDetectionLogsByPage(@RequestParam SortDirection order,
      @RequestParam int page) {
    return detectionLogService.getDetectionLogsByPageSorted(page, order);
  }
}

