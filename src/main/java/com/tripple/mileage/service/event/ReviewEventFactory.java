package com.tripple.mileage.service.event;

import com.tripple.mileage.common.code.ResultCode;
import com.tripple.mileage.common.exception.MileException;
import com.tripple.mileage.common.type.event.EventActionType;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class ReviewEventFactory {
    private final Map<EventActionType, ReviewPointProcessable> reviewEventMap = new HashMap<>();

    public ReviewEventFactory(EventAddService addService,
                              EventModifyService modifyService,
                              EventDeleteService deleteService) {
        reviewEventMap.put(EventActionType.ADD, addService);
        reviewEventMap.put(EventActionType.MOD, modifyService);
        reviewEventMap.put(EventActionType.DELETE, deleteService);
    }

    public ReviewPointProcessable getReviewEventService(EventActionType eventActionType){
        ReviewPointProcessable result = reviewEventMap.get(eventActionType);
        if(ObjectUtils.isEmpty(result)) throw new MileException(ResultCode.RESULT_4201);
        return result;
    }
}
