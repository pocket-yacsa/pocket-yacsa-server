package pocketyacsa.server.medicine.service;

import static pocketyacsa.server.medicine.exception.MedicineErrorResponse.MEDICINE_NOT_DETECT;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import pocketyacsa.server.common.exception.BadRequestException;
import pocketyacsa.server.medicine.domain.request.DetectionReq;
import pocketyacsa.server.medicine.domain.response.MedicineRes;

@Service
public class AIDetectionService implements DetectionService {

  private final DetectionLogService detectionLogService;
  private final MedicineService medicineService;
  private final RestTemplate restTemplate;
  private final String aiUrl;

  public AIDetectionService(
      DetectionLogService detectionLogService,
      MedicineService medicineService,
      RestTemplate restTemplate, @Value("${aiURL}") String aiUrl) {
    this.detectionLogService = detectionLogService;
    this.medicineService = medicineService;
    this.restTemplate = restTemplate;
    this.aiUrl = aiUrl;
  }

  /**
   * 이미지 파일에 존재하는 의약품을 분석한 후 적절한 medicine id를 반환합니다.
   *
   * @param image 의약품 사진
   * @return image에 해당하는 medicine id
   */
  @Override
  public MedicineRes detect(MultipartFile image) {

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);

    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    body.add("image", image.getResource());

    HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

    ResponseEntity<DetectionReq> response = null;
    try {
      response = restTemplate.exchange(new URI(aiUrl), HttpMethod.POST,
          requestEntity, DetectionReq.class);
    } catch (URISyntaxException e) {
      throw new BadRequestException(MEDICINE_NOT_DETECT.getErrorResponse());
    }

    DetectionReq detectionReq = response.getBody();

    if (detectionReq.getScores() * 100 < 70) {
      throw new BadRequestException(MEDICINE_NOT_DETECT.getErrorResponse());
    }

    MedicineRes medicineRes = medicineService.getMedicineResById(detectionReq.getId());

    return medicineRes;
  }
}
