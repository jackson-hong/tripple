package com.tripple.mileage.manager;


import com.tripple.mileage.common.code.ResultCode;
import com.tripple.mileage.common.exception.MileException;
import com.tripple.mileage.common.type.event.EventActionType;
import com.tripple.mileage.controller.ResponseBase;
import com.tripple.mileage.controller.ResponseData;
import com.tripple.mileage.controller.param.EventPointParam;
import com.tripple.mileage.domain.place.Place;
import com.tripple.mileage.domain.review.Review;
import com.tripple.mileage.domain.user.User;
import com.tripple.mileage.service.UserService;
import com.tripple.mileage.service.place.PlaceService;
import com.tripple.mileage.service.point.PointService;
import com.tripple.mileage.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class EventManager {

    private final UserService userService;
    private final PlaceService placeService;
    private final ReviewService reviewService;
    private final PointService pointService;

    public ResponseBase processReviewEvent(EventPointParam param){
        validateUser(param);
        validatePlace(param);

        //TODO Design Pattern apply
        EventActionType actionType = param.getAction();

        switch (actionType){
            case ADD : addReview(param); break;
            case MOD : modReview(param); break;
            case DELETE : deleteReview(param); break;
            default: throw new MileException(ResultCode.RESULT_4201);
        }

        return ResponseData.success();
    }

    private void deleteReview(EventPointParam param) {
    }

    private void modReview(EventPointParam param) {
    }

    private void addReview(EventPointParam param) {
        validateDuplicateReview(param);
        Place place = placeService.findPresentPlaceByPlaceId(param.getPlaceId());
        User user = userService.findPresentUserByUserId(param.getUserId());
        Review review = Review.of(param, place, user);
        pointService.acquirePointsByReviewWrite(review, param);
        reviewService.save(review);
    }

    private void validatePlace(EventPointParam param) {
        placeService.findPlaceByPlaceId(param.getPlaceId()).orElseThrow(() -> new MileException(ResultCode.RESULT_4101));
    }

    private void validateUser(EventPointParam param) {
        userService.findUserByUserId(param.getUserId()).orElseThrow(() -> new MileException(ResultCode.RESULT_4001));
    }

    private void validateDuplicateReview(EventPointParam param){
        reviewService.findReviewByReviewId(param.getReviewId()).orElseThrow(() -> new MileException(ResultCode.RESULT_4301));
    }
}
