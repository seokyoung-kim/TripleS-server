package com.triples.project.dao.collection;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document
public class Card {

    @Id
    public String id;
    private String image;
    private String writer;
    private String link;
    private String title;
    private String description;
    private String platform;
    private String is_saved;
    private String category;
    private String tags;
    private String date;        //
    private String saved_count; //
    private String created_at;  //

    @Builder
    public Card(String image, String writer, String link,
                String title, String description, String platform,
                String is_saved, String category, String tags, String date,
                String saved_count, String created_at) {
        this.image = image;
        this.writer = writer;
        this.link = link;
        this.title = title;
        this.description = description;
        this.platform = platform;
        this.is_saved = is_saved;
        this.category = category;
        this.tags = tags;
        this.date = date;
        this.saved_count = saved_count;
        this.created_at = created_at;
    }
}
