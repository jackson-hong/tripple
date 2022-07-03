package com.tripple.mileage.controller.payload;

import com.tripple.mileage.common.type.PointType;
import com.tripple.mileage.common.type.event.EventActionType;
import com.tripple.mileage.domain.point.PointHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PointHistoryPayload {

    private UUID pointHistoryId;
    private PointType pointReasonType;
    private EventActionType actionType;
    private int pointAmount;
    private UUID reviewId;

    public static PointHistoryPayload from(PointHistory pointHistory){
        return PointHistoryPayload.builder()
                .pointHistoryId(pointHistory.getPointHistoryId())
                .pointReasonType(pointHistory.getPointReasonType())
                .actionType(pointHistory.getActionType())
                .pointAmount(pointHistory.getPointAmount())
                .reviewId(pointHistory.getReview().getReviewId())
                .build();
    }
}
