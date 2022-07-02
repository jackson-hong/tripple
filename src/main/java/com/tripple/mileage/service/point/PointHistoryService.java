package com.tripple.mileage.service.point;

import com.tripple.mileage.domain.point.PointHistory;
import com.tripple.mileage.domain.point.PointHistoryRepository;
import com.tripple.mileage.domain.review.Review;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PointHistoryService {

    private final PointHistoryRepository pointHistoryRepository;

    public List<PointHistory> findAllPointHistoryByReview(Review review){
        return pointHistoryRepository.findAllByReview(review);
    }

    public void save(PointHistory pointHistory){
        pointHistoryRepository.save(pointHistory);
    }
}
