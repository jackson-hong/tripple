package com.tripple.mileage.common.validation;

import com.tripple.mileage.common.code.ResultCode;
import com.tripple.mileage.common.exception.MileException;
import com.tripple.mileage.domain.review.Review;
import com.tripple.mileage.service.UserService;
import com.tripple.mileage.service.place.PlaceService;
import com.tripple.mileage.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class ValidationService {

    private final UserService userService;
    private final PlaceService placeService;
    private final ReviewService reviewService;

    public void validateReviewExistWithUserAndPlace(UUID reviewId, UUID userId, UUID placeId){
        Review review = reviewService.findExistReviewWithUserAndPlaceByReviewId(reviewId);
        if(notMatchReviewWithUser(userId, review.getUser().getUserId())) throw new MileException(ResultCode.RESULT_4302);
        if(notMatchReviewWithPlace(placeId, review.getPlace().getPlaceId())) throw new MileException(ResultCode.RESULT_4302);
    }

    public void validateReviewExist(UUID reviewId){
        reviewService.findReviewByReviewId(reviewId).orElseThrow(() -> new MileException(ResultCode.RESULT_4302));
    }

    public void validatePlaceExist(UUID placeId) {
        placeService.findPlaceByPlaceId(placeId).orElseThrow(() -> new MileException(ResultCode.RESULT_4101));
    }

    public void validateUserExist(UUID userId) {
        userService.findUserByUserId(userId).orElseThrow(() -> new MileException(ResultCode.RESULT_4001));
    }

    public void validateDuplicateReview(UUID reviewId){
        log.debug("check duplicate review : {}", reviewId.toString());
        reviewService.findReviewByReviewId(reviewId).ifPresent(review -> {throw new MileException(ResultCode.RESULT_4301);});
    }

    private boolean notMatchReviewWithPlace(UUID placeId, UUID placeIdFromReview) {
        return !placeId.equals(placeIdFromReview);
    }

    private boolean notMatchReviewWithUser(UUID userId, UUID userIdFromReview) {
        return !userIdFromReview.equals(userId);
    }
}
