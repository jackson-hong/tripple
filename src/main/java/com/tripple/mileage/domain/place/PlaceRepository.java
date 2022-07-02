package com.tripple.mileage.domain.place;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlaceRepository extends JpaRepository<Place, UUID> {

    @EntityGraph("place-review-graph")
    Place findPlaceByPlaceId(UUID placeId);
}
