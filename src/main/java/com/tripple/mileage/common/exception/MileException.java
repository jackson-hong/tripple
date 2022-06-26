package com.tripple.mileage.common.exception;

import com.tripple.mileage.common.code.ResultCode;
import lombok.Getter;

@Getter
public class MileException extends RuntimeException{

    private final ResultCode resultCode;
    private final String description;

    public MileException(ResultCode resultCode) {
        this(resultCode, resultCode.getResultMessage());
    }

    public MileException(ResultCode resultCode, String message){
        super(message);
        this.resultCode = resultCode;
        this.description = message;
    }
}
