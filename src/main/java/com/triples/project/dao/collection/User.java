package com.triples.project.dao.collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.triples.project.dto.AuthProvider;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document
public class User {

    @Id
    private String id;

    private String username;
    private String email;
    private String imageUrl;

    @JsonIgnore
    private String password;

    private AuthProvider provider;

    private Boolean emailVerified = false;

    private String providerId;



}















