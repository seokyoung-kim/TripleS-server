package com.triples.project.dto;

import com.triples.project.dao.collection.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String username;
    private String email;
    private OAuth2AccessToken oAuth2AccessToken;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey,
                           String username, String email, OAuth2AccessToken oAuth2AccessToken) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.username = username;
        this.email = email;
        this.oAuth2AccessToken = oAuth2AccessToken;
    }

    public static OAuthAttributes of(String registrationId,
                                     String userNameAttributeName,
                                     Map<String, Object> attributes, OAuth2AccessToken oAuth2AccessToken) {
        return ofGoogle(userNameAttributeName, attributes, oAuth2AccessToken);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName,
                                            Map<String, Object> attributes, OAuth2AccessToken oAuth2AccessToken) {
        return OAuthAttributes.builder()
                .username((String)attributes.get("name"))
                .email((String)attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .oAuth2AccessToken(oAuth2AccessToken)
                .build();
    }

    public User toEntity() {
        return User.builder()
                .username(username)
                .email(email)
                .role(Role.GUEST)
                .oAuth2AccessToken(oAuth2AccessToken)
                .build();
    }

}
