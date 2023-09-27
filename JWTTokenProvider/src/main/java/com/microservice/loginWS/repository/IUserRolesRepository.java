package com.microservice.loginWS.repository;

import com.microservice.loginWS.entity.UserRoles;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface IUserRolesRepository extends MongoRepository<UserRoles,String> {

    Optional<UserRoles> findByRoles(String role_user);
}
