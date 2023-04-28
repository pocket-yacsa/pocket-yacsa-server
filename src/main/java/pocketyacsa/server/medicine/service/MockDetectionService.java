package pocketyacsa.server.medicine.service;

import static pocketyacsa.server.medicine.exception.MedicineErrorResponse.MEDICINE_NOT_DETECT;

import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pocketyacsa.server.common.exception.BadRequestException;
import pocketyacsa.server.medicine.domain.response.MedicineRes;

@Service
@Primary
@RequiredArgsConstructor
public class MockDetectionService implements DetectionService {

  private final DetectionLogService detectionLogService;
  private final MedicineService medicineService;

  /**
   * 이미지 파일에 존재하는 의약품을 분석한 후 적절한 medicine id를 반환합니다.
   *
   * @param image 의약품 사진
   * @return image에 해당하는 medicine id
   */
  @Override
  public MedicineRes detect(MultipartFile image) {
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    int randomNum = ThreadLocalRandom.current().nextInt(1, 50000);

    if(randomNum % 5 == 0){
      throw new BadRequestException(MEDICINE_NOT_DETECT.getErrorResponse());
    }
    detectionLogService.save(randomNum);

    return medicineService.getMedicineResById(randomNum);
  }
}
