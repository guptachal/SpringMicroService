package com.microservice.blogws.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(value = "Comment")
public class Comment {
    @Id
    private String id;
    private String name;
    private String email;
    private String comment;
    private int like;
    private Date createdAt;
    private Date updatedAt;
    @DBRef(db="Post")
    private Post post;
}
