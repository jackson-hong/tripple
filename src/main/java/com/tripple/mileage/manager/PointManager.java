package com.tripple.mileage.manager;

import com.tripple.mileage.common.validation.ValidationService;
import com.tripple.mileage.controller.payload.PointHistoryListPayload;
import com.tripple.mileage.domain.point.PointHistory;
import com.tripple.mileage.domain.user.User;
import com.tripple.mileage.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class PointManager {

    private final UserService userService;
    private final ValidationService validationService;

    public PointHistoryListPayload getPointHistories(UUID userId){
        validationService.validateUserExist(userId);

        User user = userService.findUserWithHistories(userId);
        List<PointHistory> pointHistories = user.getPointHistories();

        return PointHistoryListPayload.of(user, pointHistories);
    }
}
