package com.example.moduleproject.services;

import com.example.moduleproject.constant.AccountType;
import com.example.moduleproject.entity.User;
import com.example.moduleproject.exceptions.UserNotFoundException;
import com.example.moduleproject.repository.UserRepository;
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
