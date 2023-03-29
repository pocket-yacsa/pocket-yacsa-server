package pocketyacsa.server.medicine.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static pocketyacsa.server.common.exception.handler.ErrorResponse.of;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pocketyacsa.server.common.exception.handler.ErrorResponse;

@Getter
@AllArgsConstructor
public enum MedicineErrorResponse {

  // 404 NOT_FOUND
  MEDICINE_NOT_EXIST(of("MEDICINE_NOT_EXIST", NOT_FOUND, "의약품이 존재하지 않습니다."));

  private ErrorResponse errorResponse;
}
