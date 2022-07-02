package com.tripple.mileage.service.event;

import com.tripple.mileage.controller.param.EventPointParam;

public interface ReviewPointProcessable {
    void reviewPointProcess(EventPointParam param);
}
