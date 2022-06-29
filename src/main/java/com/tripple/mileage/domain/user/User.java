package com.tripple.mileage.domain.user;


import com.tripple.mileage.domain.BaseEntity;
import com.tripple.mileage.domain.review.Review;

import javax.persistence.*;
import java.util.List;

@Entity
public class User extends BaseEntity {

    @Id
    private String userId;

    @Column
    private int totalPoint;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Review> reviews;
}
