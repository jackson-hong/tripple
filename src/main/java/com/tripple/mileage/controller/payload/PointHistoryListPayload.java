package com.tripple.mileage.controller.payload;

import com.tripple.mileage.domain.point.PointHistory;
import com.tripple.mileage.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PointHistoryListPayload {

    private UUID userId;
    private int totalPointAmount;
    private List<PointHistoryPayload> pointHistoryList;

    public static PointHistoryListPayload of(User user, List<PointHistory> pointHistories){
        List<PointHistoryPayload> pointHistoryList = pointHistories
                .stream()
                .map(PointHistoryPayload::from)
                .collect(Collectors.toList());

        return PointHistoryListPayload.builder()
                .userId(user.getUserId())
                .totalPointAmount(user.getTotalPoint())
                .pointHistoryList(pointHistoryList)
                .build();
    }
}
