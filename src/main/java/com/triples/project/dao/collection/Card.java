package com.triples.project.dao.collection;

import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Document
public class Card {

    @Id
    public ObjectId id;
    private String image;
    private String writer;
    private String link;
    private String title;
    private String description;
    private String platform;
    private Boolean is_saved;    // 좋아요
    private String category;
    private String date;        //
    private String saved_count; // 좋아요 수
    @CreatedDate
    private String created_at; // 생성 날짜
    @LastModifiedDate
    private String updated_at; // 변경 날짜

    @Builder
    public Card(String image, String writer, String link, String title,
                String description, String platform, Boolean is_saved,
                String category, String date, String saved_count, String created_at,
                String updated_at) {
        this.image = image;
        this.writer = writer;
        this.link = link;
        this.title = title;
        this.description = description;
        this.platform = platform;
        this.is_saved = is_saved;
        this.category = category;
        this.date = date;
        this.saved_count = saved_count;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
}
