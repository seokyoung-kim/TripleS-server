package com.triples.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.triples.project.domain.Card;

public interface ICardMybatisDao extends JpaRepository<Card, Long> {

}
