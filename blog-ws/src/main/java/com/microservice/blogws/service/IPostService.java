package com.microservice.blogws.service;

import com.microservice.blogws.entity.Post;

import java.util.List;

public interface IPostService {

    Post createPost(Post post);
    List<Post> getPostByTitle(String title) throws Exception;
    List<Post> getPostByAuthor(String author) throws Exception;
    Post updatePostDescription(Post post,String upDatedDescription) throws Exception;

}
