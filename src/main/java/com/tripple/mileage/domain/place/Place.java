package com.tripple.mileage.domain.place;

import com.tripple.mileage.domain.BaseEntity;
import com.tripple.mileage.domain.review.Review;

import javax.persistence.*;
import java.util.List;

@Entity
public class Place extends BaseEntity {

    @Id
    private String placeId;

    @OneToMany(mappedBy = "place", fetch = FetchType.LAZY)
    private List<Review> reviews;
}
