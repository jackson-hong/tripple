package com.tripple.mileage.domain.user;


import com.tripple.mileage.domain.BaseEntity;
import com.tripple.mileage.domain.review.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "user_mst")
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class User extends BaseEntity {

    @Id
    @Type(type = "uuid-char")
    private UUID userId;

    @Column
    private int totalPoint;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private final List<Review> reviews = new ArrayList<>();

    public void addReview(Review review){
        this.reviews.add(review);
    }

    public void plusPoint(int gapPoint){
        this.totalPoint += gapPoint;
    }
}
