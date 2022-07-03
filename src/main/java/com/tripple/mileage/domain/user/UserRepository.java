package com.tripple.mileage.domain.user;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @EntityGraph("user-point-history-graph")
    User findWithHistoriesByUserId(UUID userId);
}
