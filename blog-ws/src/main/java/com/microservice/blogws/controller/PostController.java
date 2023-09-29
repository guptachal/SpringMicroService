package com.microservice.blogws.controller;

import com.microservice.blogws.mapper.PostMapper;
import com.microservice.blogws.payload.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blog")
public class PostController {
    private PostMapper postMapper;
    @Autowired
    public PostController(PostMapper postMapper) {
        this.postMapper = postMapper;
    }
    @PostMapping("/create")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        PostDto createdPost = postMapper.createPost(postDto);
        return new ResponseEntity<>(createdPost, HttpStatus.OK);
    }
    @PostMapping("/update")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto) throws Exception {
        return new ResponseEntity<>(postMapper.updatePost(postDto),HttpStatus.OK);
    }
}
