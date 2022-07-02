package com.tripple.mileage.domain.photo;

import com.tripple.mileage.domain.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PhotoRepository extends JpaRepository<Photo, UUID> {

    List<Photo> findAllByReview(Review review);
}
