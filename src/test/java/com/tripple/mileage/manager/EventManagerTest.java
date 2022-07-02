package com.tripple.mileage.manager;

import com.tripple.mileage.common.type.YnType;
import com.tripple.mileage.domain.place.Place;
import com.tripple.mileage.domain.review.Review;
import com.tripple.mileage.domain.review.ReviewRepository;
import com.tripple.mileage.domain.user.User;
import com.tripple.mileage.service.review.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class EventManagerTest {

    @Autowired
    ReviewService reviewService;

    @Autowired
    ReviewRepository reviewRepository;

    @BeforeEach
    public void setup() {
        User user = User.builder()
                .userId(UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f745"))
                .build();

        Place place = Place.builder()
                .placeId(UUID.fromString("2e4baf1c-5acb-4efb-a1af-eddada31b00f"))
                .build();

        Review review = Review.builder()
                .reviewId(UUID.fromString("240a0658-dc5f-4878-9381-ebb7b2667772"))
                .content("Good!")
                .user(user)
                .place(place)
                .useYn(YnType.Y)
                .build();

        user.addReview(review);
        place.addReview(review);

        reviewRepository.save(review);
    }

}