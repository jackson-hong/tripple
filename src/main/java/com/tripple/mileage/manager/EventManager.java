package com.tripple.mileage.manager;


import com.tripple.mileage.common.type.event.EventActionType;
import com.tripple.mileage.common.validation.ValidationService;
import com.tripple.mileage.controller.ResponseBase;
import com.tripple.mileage.controller.ResponseData;
import com.tripple.mileage.controller.param.EventPointParam;
import com.tripple.mileage.service.event.ReviewEventFactory;
import com.tripple.mileage.service.event.ReviewPointProcessable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class EventManager {

    private final ValidationService validationService;
    private final ReviewEventFactory reviewEventFactory;

    public ResponseBase processReviewEvent(EventPointParam param){
        // Validation
        validationService.validateUserExist(param.getUserId());
        validationService.validatePlaceExist(param.getPlaceId());

        // Get Valid Processor
        EventActionType actionType = param.getAction();
        ReviewPointProcessable reviewPointProcessor = reviewEventFactory.getReviewEventService(actionType);

        // Process
        reviewPointProcessor.reviewPointProcess(param);
        return ResponseData.success();
    }
}
