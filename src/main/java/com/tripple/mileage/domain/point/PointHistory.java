package com.tripple.mileage.domain.point;

import com.tripple.mileage.common.type.PointType;
import com.tripple.mileage.common.type.event.EventActionType;
import com.tripple.mileage.controller.param.EventPointParam;
import com.tripple.mileage.domain.BaseEntity;
import com.tripple.mileage.domain.review.Review;
import com.tripple.mileage.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "point_history")
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PointHistory extends BaseEntity {

    @Id
    @Type(type = "uuid-char")
    private UUID pointHistoryId;

    @Enumerated(EnumType.STRING)
    private PointType pointReasonType;

    @Enumerated(EnumType.STRING)
    private EventActionType actionType;

    @Column
    private int pointAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewId")
    private Review review;

    public static PointHistory of(PointType pointReasonType, EventPointParam param, int pointAmount, Review review, User user){
        return PointHistory.builder()
                .pointHistoryId(UUID.randomUUID())
                .actionType(param.getAction())
                .pointReasonType(pointReasonType)
                .pointAmount(pointAmount)
                .review(review)
                .user(user)
                .build();
    }
}
