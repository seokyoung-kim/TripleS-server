package com.triples.project.dao.collection;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document
public class Card {

    @Id
    @Indexed(name="index", unique = true)
    public String id;
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
    private String created_at;  // 크롤링한 날짜

    @Builder
    public Card(String image, String writer, String link, String title, String description, String platform, Boolean is_saved, String category, String date, String saved_count, String created_at) {
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
    }
}
