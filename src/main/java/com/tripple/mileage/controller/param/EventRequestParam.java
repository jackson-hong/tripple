package com.tripple.mileage.controller.param;

import com.tripple.mileage.common.type.event.EventActionType;
import com.tripple.mileage.common.type.event.EventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class EventRequestParam {

    private EventType type;
    private EventActionType action;
    private String reviewId;
    private String content;
    private List<String> attachedPhotoIds;
    @NotNull
    private String userId;
    private String placeId;

}
