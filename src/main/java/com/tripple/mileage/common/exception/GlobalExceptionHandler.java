package com.tripple.mileage.common.exception;

import com.tripple.mileage.controller.ResponseData;
import com.tripple.mileage.controller.payload.ErrorPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseData<ErrorPayload>> unhandledException(Exception e){
        log.error("unhandled error occurred : {}", e.getMessage());
        ErrorPayload errorPayload = ErrorPayload.builder()
                                    .message("Unhandled Exception")
                                    .detailMessage(e.getClass().getSimpleName())
                                    .description(e.getMessage())
                                    .build();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseData.error(errorPayload));
    }

    @ExceptionHandler(MileException.class)
    public ResponseEntity<ResponseData<ErrorPayload>> mileException(MileException e){
        log.error("MileException: {}", e.getMessage());
        ErrorPayload errorPayload = ErrorPayload.builder()
                .message(e.getResultCode().getResultMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseData<>(e.getResultCode(), errorPayload));
    }

}
