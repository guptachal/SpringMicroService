package com.microservice.blogws.payload;

import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private List<PostDto> posts;
    private  int pageNo;
    private int pageSize;
    private long totalElements;
    private long totalPages;
    private boolean isLast;

    }
