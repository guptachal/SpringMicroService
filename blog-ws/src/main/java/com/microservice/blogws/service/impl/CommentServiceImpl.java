package com.microservice.blogws.service.impl;

import com.microservice.blogws.entity.Comment;
import com.microservice.blogws.entity.Post;
import com.microservice.blogws.exception.ResourceNotFoundException;
import com.microservice.blogws.repositories.ICommentRepository;
import com.microservice.blogws.repositories.IPostRepository;
import com.microservice.blogws.service.ICommentService;
import com.microservice.blogws.service.IPostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class CommentServiceImpl implements ICommentService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private IPostRepository postRepository;
    private ICommentRepository commentRepository;
    @Autowired
    public CommentServiceImpl(IPostRepository postRepository, ICommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment createComment(String postId, Comment comment) {
        if (postId == null) {
            logger.info(new Date().toString());
            logger.info(getClass().toString());
            logger.error("Post Id cannot be null!");
            throw new IllegalArgumentException("Post Id cannot be null!");
        }

        Post currentPost = postRepository.findById(postId)
                .orElseThrow(() -> {
                    logger.info(new Date().toString());
                    logger.info(getClass().toString());
                    logger.info("Unable to find the post with the post id " + postId);
                    logger.error("Resource not found!");
                    return new ResourceNotFoundException("Post not found with Id: " + postId);
                });

        if (comment == null) {
            logger.info(new Date().toString());
            logger.info(getClass().toString());
            logger.error("Comment cannot be null!");
            throw new IllegalArgumentException("Comment cannot be null!");
        }
        comment.setCreatedAt(new Date());
        comment.setUpdatedAt(new Date());
        comment.setPost(currentPost);
        return commentRepository.save(comment);
    }
    @Override
    public Comment updateComment(String commentId, Comment comment) throws Exception {
        if(commentId == null){
            logger.error("Comment Id cannot be null!");
            throw new IllegalArgumentException("Comment Id cannot be null!");
        }
        Comment currentComment = commentRepository.findById(commentId).orElseThrow(
                ()->{
                    logger.info(new Date().toString());
                    logger.info(getClass().toString());
                    logger.info("Unable to find the comment with the id: "+commentId);
                    logger.error("Resource not found!");
                    return new ResourceNotFoundException("Comment cannot be found with the comment id: "+commentId);
                });
        if (comment == null) {
            logger.info(new Date().toString());
            logger.info(getClass().toString());
            logger.error("Comment cannot be null!");
            throw new IllegalArgumentException("Comment cannot be null!");
        }
        currentComment.setComment(comment.getComment());
        currentComment.setName(comment.getName());
        currentComment.setUpdatedAt(new Date());
        return commentRepository.save(currentComment);
    }
    @Override
    public void deleteComment(String postId, String commentId) throws Exception {
        if(postId == null || commentId == null){
            logger.info(new Date().toString());
            logger.info(getClass().toString());
            logger.info("Comment or post cannot be null!");
            logger.error("Resource not found!");
            throw new IllegalArgumentException("Comment or post cannot be null!");
        }
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()->{
                    logger.info(new Date().toString());
                    logger.info(getClass().toString());
                    logger.info("Couldn't find the comment with the commentId: "+commentId);
                    logger.error("Resource Not found!");
                    return new ResourceNotFoundException("Cannot found the comment with the comment id: "+commentId);
                });
        Post post = postRepository.findById(postId).orElseThrow(
                ()->{
                    logger.info(new Date().toString());
                    logger.info(getClass().toString());
                    logger.info("Couldn't find the post with the postId: "+postId);
                    logger.error("Resource Not found!");
                    return new ResourceNotFoundException("Cannot found the comment with the comment id: "+postId);
                });
        if(!comment.getPost().getId().equals(postId)){
            logger.info(new Date().toString());
            logger.info(getClass().toString());
            logger.info("Comment doesn't exist or it doesn't belong to the post!");
            logger.error("Resource not found!");
            throw new ResourceNotFoundException("Comment doesn't belong to the specific post");
        }
        commentRepository.deleteById(commentId);
    }
}
