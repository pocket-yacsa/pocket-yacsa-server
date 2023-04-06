package pocketyacsa.server.medicine.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pocketyacsa.server.medicine.service.DetectionService;

@RestController
@RequestMapping("/detection")
@RequiredArgsConstructor
public class DetectionController {

  private final DetectionService detectionService;

  /**
   * 이미지 파일에 존재하는 의약품을 분석한 후 적절한 medicine id를 반환합니다.
   *
   * @param image 의약품 사진
   * @return image에 해당하는 medicine id
   */
  public int detect(MultipartFile image) {
    return detectionService.detect(image);
  }
}
