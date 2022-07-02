package com.tripple.mileage.domain.place;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.tripple.mileage.common.type.YnType;
import com.tripple.mileage.config.jpa.JpaConfig;
import com.tripple.mileage.config.jpa.QueryDslConfig;
import com.tripple.mileage.domain.review.Review;
import com.tripple.mileage.domain.review.ReviewRepository;
import com.tripple.mileage.domain.user.User;
import com.tripple.mileage.domain.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Slf4j
@Import({
        PlaceQueryRepository.class,
        QueryDslConfig.class,
        JpaConfig.class,
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PlaceQueryRepositoryTest {

    @Autowired
    PlaceQueryRepository placeQueryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    ReviewRepository reviewRepository;

    private ListAppender<ILoggingEvent> listAppender;

    @PersistenceContext
    EntityManager entityManager;

    @BeforeEach
    public void setup() {
        User user = User.builder()
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .build();

        Place place = Place.builder()
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .build();

        Review review = Review.builder()
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667772")
                .content("Good!")
                .user(user)
                .place(place)
                .useYn(YnType.Y)
                .build();

        user.addReview(review);
        place.addReview(review);

        reviewRepository.save(review);

        listAppender = new ListAppender<>();
        Logger logger = (Logger) LoggerFactory.getLogger("org.hibernate.SQL");
        logger.setLevel(Level.DEBUG);
        logger.addAppender(listAppender);
        listAppender.start();
    }

    @Test
    @DisplayName("N+1 문제 발생 여부 테스트")
    @Transactional
    void testGetPlaceIfUnnecessaryQuery(){
        // GIVEN
        String placeId = "2e4baf1c-5acb-4efb-a1af-eddada31b00f";

        // WHEN
        Place result = placeQueryRepository.findPlaceWithReviews(placeId);
        int reviewSize = result.getReviews().size();
        result.getReviews().forEach(review -> {
            log.debug("review content : {}", review.getContent());
        });

        // THEN
        List<ILoggingEvent> testLogs = listAppender
                .list.stream()
                .filter(iLoggingEvent -> iLoggingEvent.getMessage().contains("select"))
                .collect(Collectors.toList());
        assertThat(testLogs.size()).isEqualTo(1);
        assertThat(reviewSize).isEqualTo(1);
    }
}