package com.microservice.blogws.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "Post")
public class Post {
    @Id
    private String id;
    @NotNull
    private String title;
    private String description;
    private String content;
    private Date createdAt;
    private Date updatedAt;
    private String author;
    @DBRef(db = "Comment")
    private Set<Comment> commentSet;
}
