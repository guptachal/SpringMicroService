package com.microservice.blogws.service;

import com.microservice.blogws.entity.Comment;

public interface ICommentService {
    Comment createComment(String postId, Comment comment) throws Exception;
    Comment updateComment(String commentId, Comment comment) throws Exception;
    void deleteComment(String postId, String commentId) throws Exception;
}
