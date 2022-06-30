package com.tripple.mileage.manager;


import com.tripple.mileage.controller.ResponseBase;
import com.tripple.mileage.controller.ResponseData;
import com.tripple.mileage.controller.param.EventRequestParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class EventManager {

    public ResponseBase processReviewEvent(EventRequestParam param){
        return ResponseData.success();
    }
}
