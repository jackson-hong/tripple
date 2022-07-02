package com.tripple.mileage.service.point;

import com.tripple.mileage.common.type.PointType;
import com.tripple.mileage.controller.param.EventPointParam;
import com.tripple.mileage.domain.place.Place;
import com.tripple.mileage.domain.point.PointHistory;
import com.tripple.mileage.domain.review.Review;
import com.tripple.mileage.domain.user.User;
import com.tripple.mileage.service.UserService;
import com.tripple.mileage.service.place.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PointService {

    private final UserService userService;
    private final PlaceService placeService;
    private final PointHistoryService pointHistoryService;

    public void acquirePointsByReviewWrite(Review review, EventPointParam param) {
        Place place = placeService.findPlaceByPlaceIdWithReviews(param.getPlaceId());
        User user = userService.findPresentUserByUserId(param.getUserId());
        int totalAcquiredPoint = getTotalAcquiredPoint(review, param, place);
        user.adjustPoint(totalAcquiredPoint);
        userService.save(user);
    }

    private int getTotalAcquiredPoint(Review review, EventPointParam param, Place place) {
        int totalAcquiredPoint = 0;
        totalAcquiredPoint += acquirePointIfContentValid(review, param);
        totalAcquiredPoint += acquirePointsIfPhotoAttached(param);
        totalAcquiredPoint += acquirePointsIfFirstReview(param, place);
        return totalAcquiredPoint;
    }

    private int acquirePointsIfFirstReview(EventPointParam param, Place place) {
        if(place.getReviews().size() > 0) return 0;
        int pointAmount = 1;
        savePointHistory(PointType.PLACE_FIRST, param, pointAmount);
        return pointAmount;
    }

    private void savePointHistory(PointType pointType, EventPointParam param, int pointAmount) {
        PointHistory pointHistory = PointHistory.of(pointType, param, pointAmount);
        pointHistoryService.save(pointHistory);
    }

    private int acquirePointsIfPhotoAttached(EventPointParam param) {
        if(param.getAttachedPhotoIds().size() == 0) return 0;
        int pointAmount = 1;
        savePointHistory(PointType.PHOTO, param, pointAmount);
        return pointAmount;
    }

    private int acquirePointIfContentValid(Review review, EventPointParam param) {
        if(review.getContent().length() == 0) return 0;
        int pointAmount = 1;
        savePointHistory(PointType.REVIEW, param, pointAmount);
        return pointAmount;
    }
}
