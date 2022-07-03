package com.tripple.mileage.controller.event;

import com.tripple.mileage.controller.ResponseData;
import com.tripple.mileage.controller.payload.PointHistoryListPayload;
import com.tripple.mileage.manager.PointManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final PointManager pointManager;

    // Security를 통한 Auth 처리 필요
    @GetMapping("/users/{userId}/points")
    public ResponseData<PointHistoryListPayload> getReviewHistory(@PathVariable UUID userId){
        return ResponseData.success(pointManager.getPointHistories(userId));
    }
}
