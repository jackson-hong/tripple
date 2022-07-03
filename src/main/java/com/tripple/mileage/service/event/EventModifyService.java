package com.tripple.mileage.service.event;

import com.tripple.mileage.common.validation.ValidationService;
import com.tripple.mileage.controller.param.EventPointParam;
import com.tripple.mileage.domain.photo.Photo;
import com.tripple.mileage.domain.review.Review;
import com.tripple.mileage.service.point.PointService;
import com.tripple.mileage.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventModifyService implements ReviewPointProcessable {

    private final ReviewService reviewService;
    private final PointService pointService;
    private final ValidationService validationService;

    @Override
    public void reviewPointProcess(EventPointParam param) {
        // Validation
        log.info("Review Modify - Validation Start {}", param.getReviewId());
        validationService.validateReviewExistWithUserAndPlace(param.getReviewId(), param.getUserId(), param.getPlaceId());
        Review review = reviewService.findExistReviewWithUserAndPlaceByReviewId(param.getReviewId());
        // 포인트 계산
        log.info("Review Modify - Calculate Point");
        pointService.adjustPointByReviewMod(review, param);
        // 리뷰 수정
        log.info("Review Modify - Modify Review and Save");
        List<Photo> photos = Photo.makePhotoList(param.getAttachedPhotoIds(), review);
        review.replacePhotos(photos);
        review.replaceContent(param.getContent());
        reviewService.save(review);
    }
}
