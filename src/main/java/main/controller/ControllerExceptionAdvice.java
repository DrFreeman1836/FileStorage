package main.controller;

import main.dto.RsDto;
import main.exception.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionAdvice {

  @ExceptionHandler(UserException.class)
  public ResponseEntity<RsDto> handleUserException(UserException ex) {
    ex.printStackTrace();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RsDto.builder().result(false).data(ex.getMessage()).build());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleException(Exception ex) {
    ex.printStackTrace();
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка сервера");
  }

}
