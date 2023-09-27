package com.microservice.loginWS.repository;

import com.microservice.loginWS.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface IUserRepository extends MongoRepository<User,String> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameOrEmail(String usernameOrEmail);
    User findUserByUsername(String username);
}