package com.triples.project.dao.collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.triples.project.dto.AuthProvider;
import com.triples.project.dto.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document
public class User {

    @Id
    private String id;

    private String username;
    private String email;
    private String imageUrl;
    private Role role;

    @JsonIgnore
    private String password;

    private AuthProvider provider;

    private Boolean emailVerified = false;

    private String providerId;

    private List<Card> selected_categories;
    private List<Card> saved_cards;
    private List<Card> blocked_cards;

    @CreatedDate
    private String created_at; // 생성 날짜
    @LastModifiedDate
    private String updated_at; // 변경 날짜

    @Builder
    public User(String id, String username, String email, String imageUrl,
                Role role, String password, AuthProvider provider,
                Boolean emailVerified, String providerId, List<Card> selected_categories,
                List<Card> saved_cards, List<Card> blocked_cards,
                String created_at, String updated_at) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.imageUrl = imageUrl;
        this.role = role;
        this.password = password;
        this.provider = provider;
        this.emailVerified = emailVerified;
        this.providerId = providerId;
        this.selected_categories = selected_categories;
        this.saved_cards = saved_cards;
        this.blocked_cards = blocked_cards;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
}















