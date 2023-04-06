package pocketyacsa.server.common.exception.handler;

import static org.springframework.http.HttpStatus.*;

import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import pocketyacsa.server.common.exception.BadRequestException;

@RestController
@ControllerAdvice
public class GlobalExceptionHandlers {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handlerMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    ErrorResponse errorResponse = new ErrorResponse("VALIDATION_ERROR", BAD_REQUEST,
        e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ErrorResponse> handlerBadRequestException(BadRequestException e) {
    ErrorResponse errorResponse = e.getErrorResponse();
    HttpStatus httpStatus = errorResponse.getHttpStatus();
    return new ResponseEntity<>(errorResponse, httpStatus);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponse> handlerConstraintViolationException(
      ConstraintViolationException e) {
    String message = e.getConstraintViolations().iterator().next().getMessage();
    ErrorResponse errorResponse = new ErrorResponse("VALIDATION_EXCEPTION", BAD_REQUEST, message);
    return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
  }
}
