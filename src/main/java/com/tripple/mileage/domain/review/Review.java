package com.tripple.mileage.domain.review;

import com.tripple.mileage.common.type.YnType;
import com.tripple.mileage.domain.BaseEntity;
import com.tripple.mileage.domain.place.Place;
import com.tripple.mileage.domain.user.User;

import javax.persistence.*;

@Entity
public class Review extends BaseEntity {

    @Id
    private String reviewId;

    @Column
    private String content;

    @Column
    @Enumerated(EnumType.STRING)
    private YnType useYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "placeId")
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

}
