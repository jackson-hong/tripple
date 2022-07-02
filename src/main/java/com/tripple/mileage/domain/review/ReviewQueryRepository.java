package com.tripple.mileage.domain.review;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tripple.mileage.domain.photo.QPhoto;
import com.tripple.mileage.domain.place.QPlace;
import com.tripple.mileage.domain.user.QUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ReviewQueryRepository {
    private final QPlace place = QPlace.place;
    private final QReview review = QReview.review;
    private final QUser user = QUser.user;
    private final QPhoto photo = QPhoto.photo;

    private final JPAQueryFactory jpaQueryFactory;

    public Review findReviewWithUserAndPlaceByReviewId(UUID reviewId){
        Review result = jpaQueryFactory.selectFrom(review)
                .join(review.place, place).fetchJoin()
                .join(review.user, user).fetchJoin()
                .where(review.reviewId.eq(reviewId))
                .fetchAll().fetchOne();
        return result;
    }
}
