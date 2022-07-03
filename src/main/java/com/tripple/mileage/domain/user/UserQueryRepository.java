package com.tripple.mileage.domain.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tripple.mileage.domain.photo.QPhoto;
import com.tripple.mileage.domain.place.QPlace;
import com.tripple.mileage.domain.point.QPointHistory;
import com.tripple.mileage.domain.review.QReview;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserQueryRepository {

    private final QPlace place = QPlace.place;
    private final QReview review = QReview.review;
    private final QUser user = QUser.user;
    private final QPhoto photo = QPhoto.photo;
    private final QPointHistory pointHistory = QPointHistory.pointHistory;

    private final JPAQueryFactory jpaQueryFactory;

}
