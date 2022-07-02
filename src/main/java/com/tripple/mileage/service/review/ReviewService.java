package com.tripple.mileage.service.review;

import com.tripple.mileage.domain.review.Review;
import com.tripple.mileage.domain.review.ReviewQueryRepository;
import com.tripple.mileage.domain.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewQueryRepository reviewQueryRepository;

    public Review findExistReviewWithUserAndPlaceByReviewId(UUID reviewId){
        return reviewQueryRepository.findReviewWithUserAndPlaceByReviewId(reviewId);
    }

    public Optional<Review> findReviewByReviewId(UUID reviewId){
        return reviewRepository.findById(reviewId);
    }

    public Review findPresentReviewByReviewId(UUID reviewId){
        return reviewRepository.findById(reviewId).get();
    }

    public void save(Review review) {
        reviewRepository.save(review);
    }

    public void delete(Review review){
        review.delete();
        save(review);
    }
}
