package com.tripple.mileage.domain.point;
import com.tripple.mileage.common.type.PointType;
import com.tripple.mileage.common.type.event.EventActionType;
import com.tripple.mileage.domain.BaseEntity;
import com.tripple.mileage.domain.user.User;

import javax.persistence.*;

@Entity
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
}
