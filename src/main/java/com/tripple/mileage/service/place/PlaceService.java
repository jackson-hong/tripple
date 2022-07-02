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
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final PlaceQueryRepository placeQueryRepository;

    public Optional<Place> findPlaceByPlaceId(UUID placeId){
        return placeRepository.findById(placeId);
    }

    public Place findPresentPlaceByPlaceId(UUID placeId){
        return placeRepository.findById(placeId).orElseThrow(() -> new MileException(ResultCode.RESULT_4101));
    }

    public Place findPlaceByPlaceIdWithReviews(UUID placeId){
        return placeRepository.findPlaceByPlaceId(placeId);
    }
}
