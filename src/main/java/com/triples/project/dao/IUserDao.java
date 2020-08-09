package com.triples.project.dao;

import com.triples.project.dao.collection.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IUserDao extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);
}
