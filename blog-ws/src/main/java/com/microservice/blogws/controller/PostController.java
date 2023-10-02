package com.microservice.blogws.controller;

import com.microservice.blogws.mapper.PostMapper;
import com.microservice.blogws.payload.PostDto;
import com.microservice.blogws.payload.PostResponse;
import com.microservice.blogws.utils.AppConstants;
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
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts( @RequestParam(value = "pageNo",
                                                            defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false)
                                                                int pageNo,
                                                     @RequestParam(value = "pageSize",
                                                             defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false)
                                                                int pageSize,
                                                     @RequestParam(value = "sortBy",
                                                             defaultValue = AppConstants.DEFAULT_SORT_BY, required = false)
                                                                String sortBy,
                                                     @RequestParam(value="sortDir"
                                                             ,defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,required = false)
                                                                String sortDir)throws Exception{
        PostResponse response = postMapper.getAllPost(pageNo,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}