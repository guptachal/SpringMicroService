package com.microservice.blogws.mapper;

import com.microservice.blogws.entity.Post;
import com.microservice.blogws.payload.PostDto;
import com.microservice.blogws.payload.PostResponse;
import com.microservice.blogws.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostMapper {
    private CommonMapper mapper;
    private IPostService service;
    @Autowired
    public PostMapper(CommonMapper mapper, IPostService service) {
        this.mapper = mapper;
        this.service = service;
    }
    public PostDto createPost(PostDto postDto){
        Post post = mapper.convertToEntity(postDto,Post.class);
        Post createdPost = service.createPost(post);
        return mapper.convertToResponse(createdPost, PostDto.class);
    }
    public List<PostDto> getPostByTitle(String title) throws Exception {
        try {
            List<Post> postList = service.getPostByTitle(title);
            List<PostDto> postDtoList = mapper.covertToResponseList(postList, PostDto.class);
            return postDtoList;
        }
        catch (Exception ex){
            throw new Exception();
        }
    }
    public List<PostDto> getPostByAuthor(String author) throws Exception {
        try {
            List<Post> postList = service.getPostByAuthor(author);
            List<PostDto> postDtoList = mapper.covertToResponseList(postList, PostDto.class);
            return postDtoList;
        }
        catch (Exception ex){
            throw new Exception();
        }
    }
    public PostDto updatePost(PostDto postDto) throws Exception {
        try{
            if(postDto!=null){
                Post post = mapper.convertToEntity(postDto, Post.class);
                Post updatedPost = service.updatePost(post);
                return mapper.convertToResponse(updatedPost, PostDto.class);
            }else{
                throw new Exception();
            }
        }
        catch (Exception ex){
            throw new Exception();
        }
    }
    public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) throws Exception {
        try {
            Page<Post> posts = service.getAllPost(pageNo, pageSize, sortBy, sortDir);
            posts.forEach(x->System.out.println(x));
            Page<PostDto> postDtos = mapper.convertToResponsePage(posts, PostDto.class);

            // Create a new PostResponse instance and set the posts property
            PostResponse response = new PostResponse();
            response.setPosts(postDtos.stream().toList());
            response.setPageNo(pageNo);
            response.setPageSize(pageSize);
            response.setTotalElements(postDtos.getTotalElements());
            response.setTotalPages(postDtos.getTotalPages());
            response.setLast(posts.isLast());
            return response;
        } catch (Exception ex) {
            // Handle exceptions here, e.g., log or rethrow
            throw ex;
        }
    }
}