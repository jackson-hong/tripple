package com.tripple.mileage.domain.review;

import com.tripple.mileage.common.type.YnType;
import com.tripple.mileage.controller.param.EventPointParam;
import com.tripple.mileage.domain.BaseEntity;
import com.tripple.mileage.domain.place.Place;
import com.tripple.mileage.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "review_mst")
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Review extends BaseEntity {

    @Id
    private String reviewId;

    @Column
    private String content;

    @Column
    @Enumerated(EnumType.STRING)
    private YnType useYn = YnType.Y;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "placeId")
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "userId")
    private User user;

    public static Review of(EventPointParam param, Place place, User user){
        return Review.builder()
                .reviewId(param.getReviewId())
                .content(param.getContent())
                .place(place)
                .user(user)
                .build();
    }

}
