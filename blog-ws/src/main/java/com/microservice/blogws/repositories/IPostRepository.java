package com.microservice.blogws.repositories;

import com.microservice.blogws.entity.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPostRepository extends MongoRepository<Post, String> {

    List<Post> getPostByTitle(String title);
    List<Post> getPostByAuthor(String title);
}
