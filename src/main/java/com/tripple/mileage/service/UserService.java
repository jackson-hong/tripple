package com.tripple.mileage.service;

import com.tripple.mileage.common.code.ResultCode;
import com.tripple.mileage.common.exception.MileException;
import com.tripple.mileage.domain.user.User;
import com.tripple.mileage.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> findUserByUserId(UUID userId){
        return userRepository.findById(userId);
    }

    public User findPresentUserByUserId(UUID userId){
        return userRepository.findById(userId).orElseThrow(() -> new MileException(ResultCode.RESULT_4001));
    }

    public void adjustPointAndSave(User user, int acquiredPoint){
        user.plusPoint(acquiredPoint);
        save(user);
    }

    public User findUserWithHistories(UUID userId){
        return userRepository.findWithHistoriesByUserId(userId);
    }

    public void save(User user){
        userRepository.save(user);
    }
}
