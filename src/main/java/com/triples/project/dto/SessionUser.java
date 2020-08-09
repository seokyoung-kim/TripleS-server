package com.triples.project.dto;

import com.triples.project.dao.collection.User;
import lombok.Getter;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {

    private String username;
    private String email;
    private OAuth2AccessToken access_token;

    public SessionUser(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.access_token = user.getOAuth2AccessToken();
    }
}
