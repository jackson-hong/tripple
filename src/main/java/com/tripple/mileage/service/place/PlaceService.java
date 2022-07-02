package com.tripple.mileage.service.place;

import com.tripple.mileage.common.code.ResultCode;
import com.tripple.mileage.common.exception.MileException;
import com.tripple.mileage.domain.place.Place;
import com.tripple.mileage.domain.place.PlaceQueryRepository;
import com.tripple.mileage.domain.place.PlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final PlaceQueryRepository placeQueryRepository;

    public Optional<Place> findPlaceByPlaceId(String placeId){
        return placeRepository.findById(placeId);
    }

    public Place findPresentPlaceByPlaceId(String placeId){
        return placeRepository.findById(placeId).orElseThrow(() -> new MileException(ResultCode.RESULT_4101));
    }

    public Place findPlaceByPlaceIdWithReviews(String placeId){
        return placeQueryRepository.findPlaceWithReviews(placeId);
    }
}
