package com.tripple.mileage.controller;

import com.tripple.mileage.common.code.ResultCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class ResponseBase {

    private ResultCode resultCode;
    private LocalDateTime systemDt;

    public ResponseBase(ResultCode resultCode){
        this.resultCode = resultCode;
        this.systemDt = LocalDateTime.now();
    }

    public String getResultCode(){
        return resultCode.getResultCode();
    }

    public String getResultMessage(){
        return resultCode.getResultMessage();
    }

    public String getSystemDt(){
        return systemDt.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

}
