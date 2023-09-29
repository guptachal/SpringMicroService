package com.microservice.blogws.service.impl;

import com.microservice.blogws.entity.Post;
import com.microservice.blogws.exception.BlogAPIException;
import com.microservice.blogws.exception.ResourceNotFoundException;
import com.microservice.blogws.repositories.IPostRepository;
import com.microservice.blogws.service.IPostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
@Service
public class PostServiceImpl implements IPostService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private IPostRepository postRepository;
    @Autowired
    public PostServiceImpl(IPostRepository postRepository) {
        this.postRepository = postRepository;
    }
    @Override
    public Post createPost(Post post) {
        try{
            if (post == null) {
                logger.warn("Received a null 'post' parameter in createPost");
                throw new IllegalArgumentException("Post cannot be null");
            }
            Post newPost = postRepository.save(post);
            newPost.setCreatedAt(new Date());
            newPost.setUpdatedAt(new Date());
            return newPost;
        }catch(BlogAPIException ex){
            logger.info("Unable to create the post");
            logger.error(String.valueOf(ex));
            throw new BlogAPIException(HttpStatus.EXPECTATION_FAILED,"Failed to create the post");
        }
    }

    @Override
    public List<Post> getPostByTitle(String title) throws Exception {
        if (title == null) {
            // Log that a null 'title' parameter was detected.
            logger.warn("Received a null 'title' parameter in getPostByTitle");
            throw new IllegalArgumentException("Title cannot be null");
        }
        try{
            List<Post> postsByTitle = postRepository.getPostByTitle(title);
            return postsByTitle;
        }catch(ResourceNotFoundException ex){
            logger.info("Unable to find the post for the author: "+title);
            logger.error(String.valueOf(ex));
            throw new ResourceNotFoundException();
        }
    }

    @Override
    public List<Post> getPostByAuthor(String author) throws Exception {
        try{
            List<Post> postsByTitle = postRepository.getPostByAuthor(author);
            return postsByTitle;
        }catch(ResourceNotFoundException ex){
            logger.info("Unable to find the post for the author: "+author);
            logger.error(String.valueOf(ex));
            throw new Exception(ex);
        }
    }

    @Override
    public Post updatePostDescription(Post post, String updatedDescription) throws Exception {
        try{
            Post allPosts = postRepository.findById(post.getId()).orElseThrow();
            post.setDescription(updatedDescription);
            post.setUpdatedAt(new Date());
            Post updatedPost = postRepository.findById(post.getId()).orElseThrow();
            return updatedPost;
        }catch (ResourceNotFoundException ex){
            logger.info("Unable to find the post "+post.toString());
            logger.error(String.valueOf(ex));
            throw new Exception(ex);
        }
    }
    @Override
    public Post updatePost(Post post) throws Exception {
        try{
            if(post!=null){
                Post currentPost = postRepository.findById(post.getId()).orElseThrow();
                currentPost.setTitle(post.getTitle());
                currentPost.setDescription(post.getDescription());
                currentPost.setContent(post.getContent());
                currentPost.setUpdatedAt(new Date());
                Post updatedPost = postRepository.save(currentPost);
                return updatedPost;
            }else{
                throw new Exception("Post and title cannot be null!");
            }
        }catch (ResourceNotFoundException ex){
            logger.info("Unable to find the post with the title"+post.getTitle());
            logger.error(String.valueOf(ex));
            throw new Exception(ex);
        }
    }
}
