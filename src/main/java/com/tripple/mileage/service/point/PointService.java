package com.tripple.mileage.service.point;

import com.tripple.mileage.common.type.PointType;
import com.tripple.mileage.controller.param.EventPointParam;
import com.tripple.mileage.domain.photo.Photo;
import com.tripple.mileage.domain.place.Place;
import com.tripple.mileage.domain.point.PointHistory;
import com.tripple.mileage.domain.review.Review;
import com.tripple.mileage.domain.user.User;
import com.tripple.mileage.service.UserService;
import com.tripple.mileage.service.photo.PhotoService;
import com.tripple.mileage.service.place.PlaceService;
import com.tripple.mileage.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PointService {

    private final UserService userService;
    private final PlaceService placeService;
    private final PointHistoryService pointHistoryService;
    private final PhotoService photoService;
    private final ReviewService reviewService;

    public void acquirePointsByReviewWrite(Review review, EventPointParam param) {
        Place place = placeService.findPlaceByPlaceIdWithReviews(param.getPlaceId());
        User user = userService.findPresentUserByUserId(param.getUserId());
        int totalAcquiredPoint = calculatePointByNewReview(review, param, place);
        userService.adjustPointAndSave(user, totalAcquiredPoint);
    }

    public void adjustPointByReviewMod(Review review, EventPointParam param) {
        User user = review.getUser();
        List<Photo> photos = photoService.findPhotosByReview(review);
        int totalAcquiredPoint = calculatePointByReviewMod(param, photos);
        userService.adjustPointAndSave(user, totalAcquiredPoint);
    }


    public void deletePointsByReviewDel(Review review, EventPointParam param) {
        User user = review.getUser();
        int totalAcquiredPointToDelete = calculatePointByReviewDel(review);
        userService.adjustPointAndSave(user, totalAcquiredPointToDelete);
        savePointHistory(PointType.REVIEW, param, totalAcquiredPointToDelete);
    }

    private int calculatePointByReviewDel(Review review) {
        List<PointHistory> pointHistories = pointHistoryService.findAllPointHistoryByReview(review);
        int totalAcquiredPoint = getAllPointsFromReview(pointHistories);
        totalAcquiredPoint *= -1;
        return totalAcquiredPoint;
    }

    private int calculatePointByReviewMod(EventPointParam eventPointParam, List<Photo> photosOfReview){
        int totalAcquiredPoint = 0;
        totalAcquiredPoint += addPointIfPhotoAttachedOfNone(eventPointParam, photosOfReview);
        totalAcquiredPoint += minusPointIfPhotoDeleted(eventPointParam, photosOfReview);
        return totalAcquiredPoint;
    }

    // 기존 사진이 있는 리뷰에서 사진 삭제시 -1점
    private int minusPointIfPhotoDeleted(EventPointParam eventPointParam, List<Photo> photosOfReview) {
        if(photosOfReview.size() == 0) return 0;
        if(!ObjectUtils.isEmpty(eventPointParam.getAttachedPhotoIds()) && eventPointParam.getAttachedPhotoIds().size() > 0) return 0;
        int pointAmount = -1;
        savePointHistory(PointType.PHOTO, eventPointParam, pointAmount);
        log.info("Point Minus : Photos Removed");
        return pointAmount;
    }

    // 글만 작성한 리뷰에 사진 추가시 +1
    private int addPointIfPhotoAttachedOfNone(EventPointParam eventPointParam, List<Photo> photosOfReview) {
        if(photosOfReview.size() > 0) return 0;
        if(eventPointParam.getAttachedPhotoIds().size() == 0) return 0;
        int pointAmount = 1;
        savePointHistory(PointType.PHOTO, eventPointParam, pointAmount);
        log.info("Point Add : New Photos Attached For A Review of None");
        return pointAmount;
    }

    private int calculatePointByNewReview(Review review, EventPointParam param, Place place) {
        int totalAcquiredPoint = 0;
        totalAcquiredPoint += addPointIfContentValid(review, param);
        totalAcquiredPoint += addPointIfPhotoAttached(param);
        totalAcquiredPoint += addPointIfFirstReview(param, place);
        log.info("Acquired Points : {}", totalAcquiredPoint);
        return totalAcquiredPoint;
    }

    // 장소에 첫번째 리뷰일 경우 +1
    private int addPointIfFirstReview(EventPointParam param, Place place) {
        if(place.getReviews().size() > 1) return 0;
        int pointAmount = 1;
        savePointHistory(PointType.PLACE_FIRST, param, pointAmount);
        log.info("Point Add : First Review For The Place");
        return pointAmount;
    }

    private void savePointHistory(PointType pointType, EventPointParam param, int pointAmount) {
        User user = userService.findPresentUserByUserId(param.getUserId());
        Review review =  reviewService.findPresentReviewByReviewId(param.getReviewId());
        PointHistory pointHistory = PointHistory.of(pointType, param, pointAmount, review, user);
        pointHistoryService.save(pointHistory);
    }

    // 사진 첨부시 +1점
    private int addPointIfPhotoAttached(EventPointParam param) {
        if(ObjectUtils.isEmpty(param.getAttachedPhotoIds()) || param.getAttachedPhotoIds().size() == 0) return 0;
        int pointAmount = 1;
        savePointHistory(PointType.PHOTO, param, pointAmount);
        log.info("Point Add : Review Photo Attached");
        return pointAmount;
    }

    private int addPointIfContentValid(Review review, EventPointParam param) {
        if(ObjectUtils.isEmpty(param.getContent()) || param.getContent().length() == 0) return 0;
        int pointAmount = 1;
        savePointHistory(PointType.REVIEW, param, pointAmount);
        log.info("Point Add : Review Content Valid");
        return pointAmount;
    }

    private int getAllPointsFromReview(List<PointHistory> pointHistories) {
        return pointHistories.stream()
                .mapToInt(PointHistory::getPointAmount)
                .sum();
    }
}
