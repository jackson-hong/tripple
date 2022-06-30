package com.tripple.mileage.manager;


import com.tripple.mileage.common.code.ResultCode;
import com.tripple.mileage.common.exception.MileException;
import com.tripple.mileage.common.type.event.EventActionType;
import com.tripple.mileage.controller.ResponseBase;
import com.tripple.mileage.controller.ResponseData;
import com.tripple.mileage.controller.param.EventRequestParam;
import com.tripple.mileage.service.UserService;
import com.tripple.mileage.service.place.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class EventManager {

    private final UserService userService;
    private final PlaceService placeService;

    public ResponseBase processReviewEvent(EventRequestParam param){
        validateUser(param);
        validatePlace(param);

        //TODO Design Pattern apply
        EventActionType actionType = param.getAction();

        switch (actionType){
            case ADD : addReview(param); break;
            case MOD : modReview(param); break;
            case DELETE : deleteReview(param); break;
            default: throw new MileException(ResultCode.RESULT_4201);
        }

        return ResponseData.success();
    }

    private void deleteReview(EventRequestParam param) {
    }

    private void modReview(EventRequestParam param) {
    }

    private void addReview(EventRequestParam param) {
    }

    private void validatePlace(EventRequestParam param) {
        placeService.findPlaceByUserId(param.getPlaceId()).orElseThrow(() -> new MileException(ResultCode.RESULT_4101));
    }

    private void validateUser(EventRequestParam param) {
        userService.findUserByUserId(param.getUserId()).orElseThrow(() -> new MileException(ResultCode.RESULT_4001));
    }
}
