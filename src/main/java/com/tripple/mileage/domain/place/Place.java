package com.tripple.mileage.domain.place;

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

@Entity(name = "place_mst")
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@NamedEntityGraph(
        name = "place-review-graph",
        attributeNodes = {
                @NamedAttributeNode("reviews")
        }
)
public class Place extends BaseEntity {

    @Id
    @Type(type = "uuid-char")
    private UUID placeId;

    @OneToMany(mappedBy = "place", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<Review> reviews = new ArrayList<>();

    public void addReview(Review review){
        this.reviews.add(review);
    }
}
