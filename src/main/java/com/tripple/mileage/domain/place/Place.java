package com.tripple.mileage.domain.place;

import com.tripple.mileage.domain.BaseEntity;
import com.tripple.mileage.domain.review.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "place_mst")
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Place extends BaseEntity {

    @Id
    private String placeId;

    @OneToMany(mappedBy = "place", fetch = FetchType.LAZY)
    private final List<Review> reviews = new ArrayList<>();

    public void addReview(Review review){
        this.reviews.add(review);
    }
}
