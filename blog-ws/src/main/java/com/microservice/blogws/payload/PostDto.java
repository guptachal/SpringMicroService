package com.microservice.blogws.payload;

import lombok.*;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private String id;
    private String title;
    private String description;
    private String content;
    private Date createdAt;
    private Date updatedAt;
    private String author;
}
