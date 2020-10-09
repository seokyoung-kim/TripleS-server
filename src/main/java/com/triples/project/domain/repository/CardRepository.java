package com.triples.project.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.triples.project.domain.Card;

public interface CardRepository  extends JpaRepository<Card, Long>{

}
