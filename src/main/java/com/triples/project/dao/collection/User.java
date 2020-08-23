package com.triples.project.dao.collection;

import com.triples.project.dto.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor
@Document
public class User {

    @Id
    private String id;

    private String username;
    private String email;

    @CreatedDate
    private String created_at; // 생성 날짜
    @LastModifiedDate
    private String updated_at; // 변경 날짜

    private Role role; // 권한

    @Builder
    public User(String username, String email, Role role) {
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public User update(String username) {
        this.username = username;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

}















