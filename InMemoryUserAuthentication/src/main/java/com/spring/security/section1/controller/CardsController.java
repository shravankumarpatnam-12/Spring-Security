package com.spring.security.section1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardsController {

    @GetMapping("/cards")
    public String getAllCards() {
        return "Getting all cards details from DB";
    }
}
