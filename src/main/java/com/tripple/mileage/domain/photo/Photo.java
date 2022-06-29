package com.tripple.mileage.domain.photo;

import com.tripple.mileage.common.type.YnType;
import com.tripple.mileage.domain.BaseEntity;
import com.tripple.mileage.domain.review.Review;

import javax.persistence.*;

@Entity
public class Photo extends BaseEntity {

    @Id
    private String photoId;

    @Column
    @Enumerated(EnumType.STRING)
    private YnType useYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewId")
    private Review review;
}
