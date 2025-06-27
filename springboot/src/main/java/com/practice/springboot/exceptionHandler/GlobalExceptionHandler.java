package com.practice.springboot.exceptionHandler;

import com.practice.springboot.apiErrorResponse.ApiErrorResponse;
import com.practice.springboot.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.concurrent.CompletableFuture;

@RestControllerAdvice


public class GlobalExceptionHandler {


    @ExceptionHandler(MisMatchException.class)
    public ResponseEntity<ApiErrorResponse> misMatchException(MisMatchException ex) {
        ApiErrorResponse response = ApiErrorResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.CONFLICT.value())
                .errorCode("CARD_ID_MISMATCH")
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiErrorResponse> notFoundException(NotFoundException ex) {
        ApiErrorResponse response = ApiErrorResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .errorCode("RESOURCE_NOT_FOUND")
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AdminException.class)
    public ResponseEntity<ApiErrorResponse> adminException(AdminException ex) {
        ApiErrorResponse response = ApiErrorResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.FORBIDDEN.value())
                .errorCode("ADMIN_ONLY_ACTION")
                .build();
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(OutOfRangeException.class)
    public ResponseEntity<ApiErrorResponse> outOfRangeException(OutOfRangeException ex) {
        ApiErrorResponse response = ApiErrorResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .errorCode("OUT_OF_RANGE")
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SeatsNotAvailable.class)
    public CompletableFuture<ResponseEntity<ApiErrorResponse>> seatsNotAvailable(SeatsNotAvailable ex) {
        return CompletableFuture.completedFuture(
                new ResponseEntity<>(
                        ApiErrorResponse.builder()
                                .message(ex.getMessage())
                                .status(HttpStatus.CONFLICT.value())
                                .errorCode("SEATS_UNAVAILABLE")
                                .build(),
                        HttpStatus.CONFLICT
                )
        );
    }

    @ExceptionHandler(OccupiedException.class)
    public CompletableFuture<ResponseEntity<ApiErrorResponse>> occupiedSeats(OccupiedException ex) {
        return CompletableFuture.completedFuture(
                new ResponseEntity<>(
                        ApiErrorResponse.builder()
                                .message(ex.getMessage())
                                .status(HttpStatus.CONFLICT.value())
                                .errorCode("SEAT_OCCUPIED")
                                .build(),
                        HttpStatus.CONFLICT
                )
        );
    }
}
