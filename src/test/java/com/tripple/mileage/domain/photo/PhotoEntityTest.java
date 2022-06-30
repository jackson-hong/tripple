package com.tripple.mileage.domain.photo;

import com.tripple.mileage.domain.place.Place;
import com.tripple.mileage.domain.place.PlaceRepository;
import com.tripple.mileage.domain.review.Review;
import com.tripple.mileage.domain.review.ReviewRepository;
import com.tripple.mileage.domain.user.User;
import com.tripple.mileage.domain.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.PropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PhotoEntityTest {

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @BeforeEach
    void setUp(){
        User user = User.builder()
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .build();

        userRepository.save(user);

        Place place = Place.builder()
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .build();

        placeRepository.save(place);

        Review review = Review.builder()
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667772")
                .content("Good!")
                .user(user)
                .place(place)
                .build();

        reviewRepository.save(review);
    }

    @Test
    @DisplayName("Photo Entity DB 연동 확인 테스트")
    void testPhotoEntitySync(){
        // GIVEN
        Review review = Review.builder()
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667772")
                .build();

        Photo photo = Photo.builder()
                .photoId("afb0cef2-851d-4a50-bb07-9cc15cbdc332")
                .review(review)
                .build();

        // WHEN
        photoRepository.save(photo);

        // THEN
        Optional<Photo> result = photoRepository.findById("afb0cef2-851d-4a50-bb07-9cc15cbdc332");
        assertThat(result.isPresent()).isTrue();
    }
}