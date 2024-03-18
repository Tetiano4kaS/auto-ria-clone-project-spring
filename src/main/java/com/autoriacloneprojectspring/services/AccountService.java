package com.autoriacloneprojectspring.services;

import com.autoriacloneprojectspring.constant.AccountType;
import com.autoriacloneprojectspring.entity.User;
import com.autoriacloneprojectspring.exceptions.UserNotFoundException;
import com.autoriacloneprojectspring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final UserRepository userRepository;

    public String buySubscribe(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User with such email " + email + " not found"));
        if (user.getAccountType() != AccountType.PREMIUM) {
            user.setAccountType(AccountType.PREMIUM);
            userRepository.save(user);
            return "You successfully bought premium account!";
        }
        return "Your premium account already exist.";
    }
}
