package com.tripple.mileage.service.event;

import com.tripple.mileage.common.validation.ValidationService;
import com.tripple.mileage.controller.param.EventPointParam;
import com.tripple.mileage.domain.photo.Photo;
import com.tripple.mileage.domain.review.Review;
import com.tripple.mileage.service.point.PointService;
import com.tripple.mileage.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventModifyService implements ReviewPointProcessable {

    private final ReviewService reviewService;
    private final PointService pointService;
    private final ValidationService validationService;

    @Override
    public void reviewPointProcess(EventPointParam param) {
        // Validation
        validationService.validateReviewExistWithUserAndPlace(param.getReviewId(), param.getUserId(), param.getPlaceId());

        // 리뷰 수정
        Review review = reviewService.findExistReviewWithUserAndPlaceByReviewId(param.getReviewId());
        List<Photo> photos = Photo.makePhotoList(param.getAttachedPhotoIds(), review);
        review.replacePhotos(photos);
        review.replaceContent(param.getContent());

        // 포인트 계산
        pointService.adjustPointByReviewMod(review, param);

        reviewService.save(review);
    }
}
