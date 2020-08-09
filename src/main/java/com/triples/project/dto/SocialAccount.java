package com.triples.project.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

@Getter
@RequiredArgsConstructor
public class SocialAccount {

    private String social_id;
    private OAuth2AccessToken access_token;
    private String provider;

    @Builder
    public SocialAccount(String social_id, OAuth2AccessToken access_token, String provider) {
        this.social_id = social_id;
        this.access_token = access_token;
        this.provider = provider;
    }
}
