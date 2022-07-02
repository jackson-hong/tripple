package com.tripple.mileage.domain.point;
import com.tripple.mileage.common.type.PointType;
import com.tripple.mileage.common.type.event.EventActionType;
import com.tripple.mileage.controller.param.EventPointParam;
import com.tripple.mileage.domain.BaseEntity;
import com.tripple.mileage.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "point_history")
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PointHistory extends BaseEntity {

    @Id
    private String pointHistoryId;

    @Enumerated(EnumType.STRING)
    private PointType pointReasonType;

    @Enumerated(EnumType.STRING)
    private EventActionType actionType;

    @Column
    private int pointAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    public static PointHistory of(PointType pointReasonType, EventPointParam param, int pointAmount){
        return PointHistory.builder()
                .pointHistoryId(UUID.randomUUID().toString())
                .actionType(param.getAction())
                .pointReasonType(pointReasonType)
                .pointAmount(pointAmount)
                .build();
    }
}
