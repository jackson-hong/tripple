package com.tripple.mileage.domain.place;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tripple.mileage.domain.review.QReview;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PlaceQueryRepository {

    private final QPlace place = QPlace.place;
    private final QReview review = QReview.review;

    private final JPAQueryFactory jpaQueryFactory;

    public Place findPlaceWithReviews(String placeId){
        Place result =  jpaQueryFactory.selectFrom(place)
                .join(place.reviews, review).fetchJoin()
                .where(place.placeId.eq(placeId))
                .fetchAll().fetchOne();
        return result;
    }
}
