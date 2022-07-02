package com.tripple.mileage.service.photo;

import com.tripple.mileage.domain.photo.Photo;
import com.tripple.mileage.domain.photo.PhotoRepository;
import com.tripple.mileage.domain.review.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;

    public List<Photo> findPhotosByReview(Review review){
        return photoRepository.findAllByReview(review);
    }
}
