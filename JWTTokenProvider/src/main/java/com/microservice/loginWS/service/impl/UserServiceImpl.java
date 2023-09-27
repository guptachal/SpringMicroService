package com.microservice.loginWS.service.impl;

import com.microservice.loginWS.entity.UserRoles;
import com.microservice.loginWS.domain.UserPrincipal;
import com.microservice.loginWS.entity.User;
import com.microservice.loginWS.mapper.CommonMapper;
import com.microservice.loginWS.repository.IUserRepository;
import com.microservice.loginWS.utils.Roles;
import com.microservice.loginWS.exception.ResourceNotFoundException;
import com.microservice.loginWS.exception.UsernameExistException;
import com.microservice.loginWS.repository.IUserRolesRepository;
import com.microservice.loginWS.service.IUserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Random;

@Service
@Transactional
@Qualifier("UserDetailService")
public class UserServiceImpl implements UserDetailsService, IUserService {
    private IUserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private CommonMapper mapper;
    private IUserRolesRepository rolesRepository;
    @Autowired
    public UserServiceImpl(IUserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, CommonMapper mapper, IUserRolesRepository rolesRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.mapper = mapper;
        this.rolesRepository = rolesRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()->new ResourceNotFoundException());
        return new UserPrincipal(user);

    }
    public User getUser(String username){
        return userRepository.findByUsername(username).orElseThrow(()->new ResourceNotFoundException());
    }
    @Override
    public User register(User user) throws UsernameExistException {
        validateNewUsernameAndEmail(user.getUsername(), user.getEmail());
        System.out.println(user.getUsername()+" "+user.getPassword());
        user.setUserId(generateUserId());
        user.setPassword(encodePassword(user.getPassword()));
        user.setCreatedAt(new Date());
        user.setActive(true);
        user.setNotLocked(true);
        user.setUserRoles(setUserRoles());
        return userRepository.save(user);
    }
    private UserRoles setUserRoles() {
        System.out.println("setting user roles");
        UserRoles roles =  rolesRepository.findByRoles("ROLE_USER").orElseThrow(()-> new ResourceNotFoundException());
        System.out.println("roles "+roles.toString());
        return roles;
    }
    private void validateNewUsernameAndEmail(String username, String email) throws UsernameExistException {
        User userUsingUsername  = userRepository.findUserByUsername(username);

        if(userUsingUsername != null){
            throw new UsernameExistException(" Username already exist!");
        }
    }
    private String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }
    private String generateUserId() {
        Random random = new Random();
        long timestamp = System.currentTimeMillis(); // Get current timestamp in milliseconds
        int randomPart = random.nextInt(100000); // Generate a random 5-digit number
        String uniqueId = String.format("%013d%05d", timestamp, randomPart).substring(0, 10);
        return uniqueId;

    }
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(()->new ResourceNotFoundException("username",username,"abs"));
    }
    @Override
    public User findByUsernameOrEmail(String usernameOrEmail){
        return userRepository.findByUsername(usernameOrEmail).orElseThrow(()-> new ResourceNotFoundException());
    }
    @Override
    public User updateUser(@NotNull User user) {
        System.out.println("userid:" + user.getId());
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found",user.getUsername(),""));

        existingUser.setUpdatedAt(new Date());
        existingUser.setUsername(user.getUsername());
        existingUser.setFirstName(user.getFirstName());
        return userRepository.save(existingUser);
    }

    @Override
    public User updateUserRole(String role, String username) {
        User existingUser = userRepository.findByUsername(username).orElseThrow(()-> new ResourceNotFoundException());
        if(role.equals(Roles.ROLE_USER.toString())){
            System.out.println("Here USER");
        }else if(role.equals(Roles.ROLE_ADMIN.toString())){
            System.out.println("Here ADMIN");

        }

        return userRepository.save(existingUser);
    }

    private void validateAndUpdatePreExistingUser(User user) {
        userRepository.findByUsername(user.getUsername()).orElseThrow(()-> new ResourceNotFoundException());

    }

}