package com.tripple.mileage.service;

import com.tripple.mileage.common.code.ResultCode;
import com.tripple.mileage.common.exception.MileException;
import com.tripple.mileage.domain.user.User;
import com.tripple.mileage.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public Optional<User> findUserByUserId(String userId){
        return userRepository.findById(userId);
    }

    public User findPresentUserByUserId(String userId){
        return userRepository.findById(userId).orElseThrow(() -> new MileException(ResultCode.RESULT_4001));
    }

    public void save(User user){
        userRepository.save(user);
    }
}
