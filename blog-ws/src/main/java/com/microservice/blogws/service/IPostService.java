package com.microservice.blogws.service;

import com.microservice.blogws.entity.Post;
import com.microservice.blogws.payload.PostResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IPostService {

    Post createPost(Post post);
    List<Post> getPostByTitle(String title) throws Exception;
    List<Post> getPostByAuthor(String author) throws Exception;
    Post updatePostDescription(Post post,String upDatedDescription) throws Exception;
    Post updatePost(Post post) throws Exception;
    Page<Post> getAllPost(int pageNo, int pageSize, String sortBy, String sortDir)throws Exception;
}
