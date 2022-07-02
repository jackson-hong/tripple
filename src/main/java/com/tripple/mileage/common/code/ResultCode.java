package com.tripple.mileage.common.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum ResultCode {

    RESULT_0000("0000","성공"),
    RESULT_9999("9999","실패"),

    // USER
    RESULT_4001("4001", "알 수 없는 유저입니다.", HttpStatus.UNAUTHORIZED),
    RESULT_4003("4003","권한이 유효하지 않습니다", HttpStatus.UNAUTHORIZED),

    // PLACE
    RESULT_4101("4101", "유효하지 않은 장소입니다", HttpStatus.BAD_REQUEST),

    // Validation
    RESULT_4201("4201", "유효하지 않은 요청입니다.", HttpStatus.BAD_REQUEST),

    // Review
    RESULT_4301("4301", "이미 존재하는 리뷰입니다.", HttpStatus.BAD_REQUEST),

    // Internal Validation
    RESULT_5001("5001", "유효하지 않은 파라미터입니다."),
    RESULT_5002("5002","해당 엔티티가 존재하지 않습니다."),
;


    @Getter
    private final String resultCode;

    @Getter
    private final String resultMessage;

    @Getter
    private final HttpStatus httpStatus;

    ResultCode(String resultCode, String resultMessage) {
        this(resultCode, resultMessage, HttpStatus.OK);
    }

    ResultCode(String resultCode, String resultMessage, HttpStatus httpStatus) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.httpStatus = httpStatus;
    }

    public static boolean isSuccess(String resultCode) {
        return RESULT_0000.resultCode.equals(resultCode);
    }
}
