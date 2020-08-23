package com.triples.project.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class LoginApiController {

    @GetMapping("/api/v1/login")
    public String login() {

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>");
        return "Success ADMIN";
    }

    @GetMapping("/api/v1/login2")
    public String login2() {

        return "Success MANAGER";
    }
}
