package com.microservice.blogws.mapper;

import com.microservice.blogws.payload.PostDto;
import com.microservice.blogws.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    private CommonMapper mapper;
    private IPostService service;
    @Autowired
    public PostMapper(CommonMapper mapper, IPostService service) {
        this.mapper = mapper;
        this.service = service;
    }
    PostDto
}
