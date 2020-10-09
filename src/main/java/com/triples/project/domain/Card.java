package com.triples.project.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Card {
	@Id
	@GeneratedValue
	private Long id;
	@Column(length = 100)
	private String writer;
	@Column(length = 100)
	private String title;
	@Column(length = 4000)
    private String image;
	@Column(length = 300)
    private String link;
	@Column(length = 4000)
    private String description;
	@Column(length = 100)
    private String platform;
	@Column(length = 10, columnDefinition = "")
    private Boolean is_saved;    // 좋아요
	@Column(length = 50)
    private String category;
	@Column(length = 100)
    private String date;        // 글 작성 날짜
	@Column(length = 100)
    private String saved_count; // 좋아요 수
	@Column(length = 10)
    private String image_type;
	@Column(length = 100)
    @CreatedDate
    private String created_at; // 생성 날짜
	@Column(length = 100)
    @LastModifiedDate
    private String updated_at; // 변경 날짜
	

    @Builder
    public Card(String writer) {
        this.writer = writer;
    }
}
