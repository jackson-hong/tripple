package com.tripple.mileage.domain.point;

import com.tripple.mileage.domain.review.Review;
import com.tripple.mileage.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PointHistoryRepository extends JpaRepository<PointHistory, UUID> {
    List<PointHistory> findAllByReview(Review review);
    List<PointHistory> findAllByUser(User user);
}
