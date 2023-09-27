package com.microservice.loginWS.controller;

import com.microservice.loginWS.exception.ExceptionHandling;
import com.microservice.loginWS.mapper.UserMapper;
import com.microservice.loginWS.payload.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController extends ExceptionHandling {
    private UserMapper userMapper;
    @Autowired
    public UserController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    @PostMapping("/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto user){
        System.out.println(user.getId());
        System.out.println(user.getFirstName());
        return new ResponseEntity<>(userMapper.updateUser(user), HttpStatus.OK);
    }
    @PostMapping("/update/roles")
    public ResponseEntity<UserDto> updateUserAuthorities(@RequestParam(value = "role") String role, @RequestParam(required = true, value = "username") String username){
        return new ResponseEntity<>(userMapper.updateUserRole(role,username),HttpStatus.OK);
    }
    @GetMapping("/user/admin")
    public ResponseEntity<String> testAdmin(){
        return new ResponseEntity<>("Admin", HttpStatus.OK);
    }
    @GetMapping("/user/user")
    public ResponseEntity<String> testUser(){
        return new ResponseEntity<>("Admin", HttpStatus.OK);
    }
}