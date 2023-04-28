package pocketyacsa.server.medicine.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pocketyacsa.server.medicine.domain.response.MedicineRes;
import pocketyacsa.server.medicine.service.DetectionService;

@Tag(name = "의약품 촬영", description = "의약품을 촬영하는 API 입니다.")
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
  @Operation(summary = "의약품 사진을 전송합니다.",
      description = "의약품 사진을 전송하고 결과를 받을 때까지 대기합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "의약품 사진 전송 후 탐지 성공"),
      @ApiResponse(responseCode = "404 (MEMBER_NOT_EXIST)", description = "회원가입 정보 없음",
          content = @Content(schema = @Schema(
              example = "{\n"
                  + "  \"name\": \"MEMBER_NOT_EXIST\",\n"
                  + "  \"httpStatus\": \"NOT_FOUND\",\n"
                  + "  \"message\": \"회원이 존재하지 않습니다.\"\n"
                  + "}"))),
      @ApiResponse(responseCode = "400 (MEDICINE_NOT_DETECT)", description = "의약품 탐지 실패",
          content = @Content(schema = @Schema(
              example = "{\n"
                  + "  \"name\": \"MEDICINE_NOT_DETECT\",\n"
                  + "  \"httpStatus\": \"BAD_REQUEST\",\n"
                  + "  \"message\": \"의약품을 탐지하지 못했습니다.\"\n"
                  + "}"))),
      @ApiResponse(responseCode = "404 (MEDICINE_NOT_EXIST)", description = "의약품 정보 없음",
          content = @Content(schema = @Schema(
              example = "{\n"
                  + "  \"name\": \"MEDICINE_NOT_EXIST\",\n"
                  + "  \"httpStatus\": \"NOT_FOUND\",\n"
                  + "  \"message\": \"의약품이 존재하지 않습니다.\"\n"
                  + "}")))
  })
  @PostMapping(consumes = {"multipart/form-data"})
  public MedicineRes detect(@RequestParam("image") MultipartFile image) {
    return detectionService.detect(image);
  }
}
