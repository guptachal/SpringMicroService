package com.microservice.blogws.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class CommonMapper {

    private final ModelMapper mapper;
    <T,S> S convertToEntity(T data, Class<S> type){
        return mapper.map(data,type);
    }
    <T,S> S convertToResponse(T data, Class<S> type){
        return mapper.map(data,type);
    }
    <T,S> List<S> covertToResponseList(List<T> lists, Class<S> type){
        return lists.stream()
                .map(list->convertToResponse(list,type))
                .collect(Collectors.toList());
    }

}
