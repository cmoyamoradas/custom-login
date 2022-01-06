package com.okta.spring.example.controllers;

import com.okta.spring.example.entities.Greeting;
import com.okta.spring.example.services.Oauth2Service;
import com.okta.spring.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ApiController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/api/greeting")
    public Greeting greeting(Authentication auth) {

        return new Greeting(counter.incrementAndGet(), String.format(template, auth.getPrincipal()));
    }
}
