package com.tripple.mileage.service.place;

import com.tripple.mileage.common.code.ResultCode;
import com.tripple.mileage.common.exception.MileException;
import com.tripple.mileage.domain.place.Place;
import com.tripple.mileage.domain.place.PlaceRepository;
import com.tripple.mileage.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlaceService {

    private final PlaceRepository placeRepository;

    public Optional<Place> findPlaceByUserId(String placeId){
        return placeRepository.findById(placeId);
    }
}
