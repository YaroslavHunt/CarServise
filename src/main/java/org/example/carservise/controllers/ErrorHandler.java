package org.example.carservise.controllers;

import org.example.carservise.dto.ErrorDTO;
import org.example.carservise.dto.ErrorExceptionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Objects;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorDTO> handleIllegalArgumentError(Exception e) {
        return ResponseEntity.badRequest()
                .body(ErrorDTO.builder()
                        .message(e.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

//    @ExceptionHandler({MethodArgumentNotValidException.class})
//    public ResponseEntity<ErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
//        String details = e
//                .getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .map(error -> error.getField()
//                        .concat(" ")
//                        .concat(error.getDefaultMessage() != null ? error.getDefaultMessage() : "Validation error")
//                )
//            .collect(Collectors.joining("\n"));
//
//        return ResponseEntity
//                .badRequest()
//                .body(ErrorDTO.builder()
//                        .message(details)
//                        .timestamp(LocalDateTime.now())
//                        .build());
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorExceptionDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ErrorExceptionDTO(400, Objects.requireNonNull(e.getFieldError()).getField(), e.getFieldError().getDefaultMessage());
    }

}
