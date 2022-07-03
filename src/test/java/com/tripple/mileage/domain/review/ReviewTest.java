package com.tripple.mileage.domain.review;

import com.tripple.mileage.config.jpa.JpaConfig;
import com.tripple.mileage.config.jpa.QueryDslConfig;
import com.tripple.mileage.controller.param.EventPointParam;
import com.tripple.mileage.domain.place.Place;
import com.tripple.mileage.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.UUID;

import static com.tripple.mileage.common.type.event.EventActionType.ADD;
import static com.tripple.mileage.common.type.event.EventType.REVIEW;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({JpaConfig.class,
        ReviewQueryRepository.class,
        QueryDslConfig.class,
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReviewTest {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ReviewQueryRepository reviewQueryRepository;

    @Test
    @DisplayName("Soft Delete를 위한 @Where 동작 확인")
    void testWhereClaus(){
        // GIVEN
        User user = User.builder()
                .userId(UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f745"))
                .build();

        Place place = Place.builder()
                .placeId(UUID.fromString("2e4baf1c-5acb-4efb-a1af-eddada31b00f"))
                .build();

        EventPointParam setupParam = EventPointParam.builder()
                .type(REVIEW)
                .action(ADD)
                .reviewId(UUID.fromString("240a0658-dc5f-4878-9381-ebb7b2667772"))
                .content("좋아요!")
                .userId(UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f745"))
                .placeId(UUID.fromString("2e4baf1c-5acb-4efb-a1af-eddada31b00f"))
                .attachedPhotoIds(Arrays.asList(UUID.fromString("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8"),
                        (UUID.fromString("afb0cef2-851d-4a50-bb07-9cc15cbdc332"))))
                .build();
        Review review = Review.of(setupParam, place, user);
        place.addReview(review);
        user.addReview(review);
        review.delete();
        reviewRepository.save(review);

        // WHEN
        // 1차 캐시 사용 X
        Review result = reviewQueryRepository.findReviewWithQuery(UUID.fromString("240a0658-dc5f-4878-9381-ebb7b2667772"));

        // THEN
        assertThat(ObjectUtils.isEmpty(result)).isTrue();
    }
}