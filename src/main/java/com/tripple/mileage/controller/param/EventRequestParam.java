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

    @NotNull
    private EventType type;
    @NotNull
    private EventActionType action;
    @NotNull
    private String reviewId;
    private String content;
    private List<String> attachedPhotoIds;
    @NotNull
    private String userId;
    @NotNull
    private String placeId;

}
