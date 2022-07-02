package com.tripple.mileage.service.event;

import com.tripple.mileage.common.validation.ValidationService;
import com.tripple.mileage.controller.param.EventPointParam;
import com.tripple.mileage.domain.photo.Photo;
import com.tripple.mileage.domain.place.Place;
import com.tripple.mileage.domain.review.Review;
import com.tripple.mileage.domain.user.User;
import com.tripple.mileage.service.UserService;
import com.tripple.mileage.service.place.PlaceService;
import com.tripple.mileage.service.point.PointService;
import com.tripple.mileage.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventAddService implements ReviewPointProcessable {

    private final UserService userService;
    private final PlaceService placeService;
    private final ReviewService reviewService;
    private final PointService pointService;
    private final ValidationService validationService;

    @Override
    public void reviewPointProcess(EventPointParam param) {
        // Validation
        validationService.validateDuplicateReview(param.getReviewId());

        // 리뷰 생성
        Place place = placeService.findPresentPlaceByPlaceId(param.getPlaceId());
        User user = userService.findPresentUserByUserId(param.getUserId());
        Review review = Review.of(param, place, user);
        addPhotosToReview(param, review);

        reviewService.save(review);
        // 포인트 계산
        pointService.acquirePointsByReviewWrite(review, param);

    }

    private void addPhotosToReview(EventPointParam param, Review review) {
        if(ObjectUtils.isEmpty(param.getAttachedPhotoIds())) return;
        List<Photo> photos = Photo.makePhotoList(param.getAttachedPhotoIds(), review);
        review.addPhotos(photos);
    }
}
