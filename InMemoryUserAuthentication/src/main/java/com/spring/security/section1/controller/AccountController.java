package com.spring.security.section1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @GetMapping("/accounts")
    public String getALlAccounts() {
        return "Getting details from DB";
    }
}
