package com.tripple.mileage.controller.event;

import com.tripple.mileage.controller.ResponseBase;
import com.tripple.mileage.controller.ResponseData;
import com.tripple.mileage.controller.param.EventRequestParam;
import com.tripple.mileage.manager.EventManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EventController {

    private final EventManager eventManager;

    @PostMapping("/events")
    public ResponseData<ResponseBase> reviewEvent(@RequestBody @Valid EventRequestParam param){
        return ResponseData.success(eventManager.processReviewEvent(param));
    }
}
