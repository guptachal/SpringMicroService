package com.microservice.blogws.payload;

import java.util.List;

public class PostResponse {
    private List<PostDto> posts;
    private  int pageNo;
    private int pageSize;
    private long totalElements;
    private long totalPages;
    private boolean isLast;
}
