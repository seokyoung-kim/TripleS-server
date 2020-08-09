package com.triples.project.controller;

import com.triples.project.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
@RestController
public class LoginApiController {

    private final HttpSession httpSession;

    @GetMapping("/")
    public SessionUser index(OAuth2AuthenticationToken authentication) {

        log.info(">>>>>>>>>>>> authentication = " + authentication);

        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        return user;
    }
}
