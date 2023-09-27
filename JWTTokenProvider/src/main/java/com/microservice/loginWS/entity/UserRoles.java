package com.microservice.loginWS.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "User_Roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoles {
    @Id
    private String id;
    private String[] roles;
    private String[] authorities;
}