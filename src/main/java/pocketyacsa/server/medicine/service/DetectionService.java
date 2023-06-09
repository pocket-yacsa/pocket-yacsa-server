package pocketyacsa.server.medicine.service;

import org.springframework.web.multipart.MultipartFile;
import pocketyacsa.server.medicine.domain.response.MedicineRes;

public interface DetectionService {

  /**
   * 이미지 파일에 존재하는 의약품을 분석한 후 적절한 medicine id를 반환합니다.
   *
   * @param image 의약품 사진
   * @return image에 해당하는 medicine id
   */
  public MedicineRes detect(MultipartFile image);

}
