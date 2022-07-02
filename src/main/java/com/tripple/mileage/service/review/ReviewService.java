package com.tripple.mileage.service.review;

import com.tripple.mileage.domain.review.Review;
import com.tripple.mileage.domain.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public Optional<Review> findReviewByReviewId(String reviewId){
        return reviewRepository.findById(reviewId);
    }

    public void save(Review review) {
        reviewRepository.save(review);
    }
}
