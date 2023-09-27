package com.microservice.blogws.payload;

import lombok.Data;

import java.util.Date;
@Data
public class PostDto {
    private String id;
    private String title;
    private String description;
    private String content;
    private Date createdAt;
    private Date updatedAt;
    private String author;
}
