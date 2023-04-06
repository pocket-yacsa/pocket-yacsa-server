package pocketyacsa.server.medicine.service;

import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Primary
@RequiredArgsConstructor
public class MockDetectionService implements DetectionService {

  private final DetectionLogService detectionLogService;

  /**
   * 이미지 파일에 존재하는 의약품을 분석한 후 적절한 medicine id를 반환합니다.
   *
   * @param image 의약품 사진
   * @return image에 해당하는 medicine id
   */
  @Override
  public int detect(MultipartFile image) {
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    int randomNum = ThreadLocalRandom.current().nextInt(1, 50000);
    detectionLogService.save(randomNum);
    return randomNum;
  }
}
