package com.tripple.mileage.common.type;

import com.tripple.mileage.common.code.ResultCode;
import com.tripple.mileage.common.exception.MileException;
import org.springframework.util.ObjectUtils;

public enum YnType {

    Y,
    N;

    public static YnType fromBoolean(Boolean expression){
        if(ObjectUtils.isEmpty(expression)) throw new MileException(ResultCode.RESULT_5001);
        return expression ? Y : N;
    }

    public static YnType from(String Yn) {
        if(ObjectUtils.isEmpty(Yn)) throw new MileException(ResultCode.RESULT_5001);
        if("Y".equalsIgnoreCase(Yn)) return Y;
        if("N".equalsIgnoreCase(Yn)) return N;
        throw new MileException(ResultCode.RESULT_5001);
    }

}
