package com.spring.security.section1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BalanceController {

    @GetMapping("/balance")
    public String getBalance() {
        return "Getting balance from DB";
    }
}
