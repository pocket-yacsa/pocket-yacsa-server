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
import pocketyacsa.server.medicine.domain.response.DetectionLogPageRes;
import pocketyacsa.server.medicine.service.DetectionLogService;

@RestController
@RequestMapping("/detection-logs")
@RequiredArgsConstructor
public class DetectionLogController {

  private final DetectionLogService detectionLogService;

  /**
   * 특정 medicine을 촬영기록에 추가합니다.
   *
   * @param medicineId 촬영기록에 추가할 medicine의 id
   * @return 즐겨찾기 추가 성공여부 메시지
   */
  @PostMapping
  public ResponseEntity<Object> save(@RequestParam int medicineId) {
    detectionLogService.save(medicineId);
    CommonResponse response =
        new CommonResponse("SAVE_DETECTION_LOG_SUCCESS", CREATED, "촬영기록 추가 성공");
    return new ResponseEntity<>(response, response.getHttpStatus());
  }

  /**
   * 특정 id의 detectionLog를 삭제합니다.
   *
   * @param detectionLogId 삭제할 detectionLog id
   * @return detectionLog 삭제 성공 여부
   */
  @DeleteMapping
  public ResponseEntity<Object> delete(@RequestParam int detectionLogId) {
    detectionLogService.delete(detectionLogId);
    CommonResponse response =
        new CommonResponse("DELETE_DETECTION_LOG_SUCCESS", OK, "촬영기록 삭제 성공");
    return new ResponseEntity<>(response, response.getHttpStatus());
  }

  /**
   * 로그인한 사용자의 모든 detectionLog를 삭제합니다.
   *
   * @return detectionLog 삭제 성공 여부
   */
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
  @GetMapping
  public DetectionLogPageRes getDetectionLogsByPage(@RequestParam int page) {
    return detectionLogService.getDetectionLogsByPage(page);
  }
}
