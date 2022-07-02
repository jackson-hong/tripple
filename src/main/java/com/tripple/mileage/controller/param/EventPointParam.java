package com.tripple.mileage.controller.param;

import com.tripple.mileage.common.type.event.EventActionType;
import com.tripple.mileage.common.type.event.EventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Getter
public class EventPointParam {

    @NotNull
    private EventType type;

    @NotNull
    private EventActionType action;

    @NotNull
    private UUID reviewId;

    private String content;

    private List<UUID> attachedPhotoIds;

    @NotNull
    private UUID userId;

    @NotNull
    private UUID placeId;

}
