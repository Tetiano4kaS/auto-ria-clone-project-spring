package com.autoriacloneprojectspring.controller;

import com.autoriacloneprojectspring.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/premiumAccount")
    public String buyPremiumAccount(@RequestParam(name = "email") String email) {
        return accountService.buySubscribe(email);
    }
}
