package com.microservice.loginWS.config.securityConfig;

import com.microservice.loginWS.service.IUserService;
import com.microservice.loginWS.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {
    private UserDetailsService userDetailsService;
    private BCryptPasswordEncoder passwordEncoder;
    private IUserService userService;

    @Autowired
    public CustomAuthenticationManager(UserDetailsService userDetailsService, BCryptPasswordEncoder passwordEncoder, IUserService userService) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;

    }
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        //UserDetails user = userDetailsService.loadUserByUsername(username);
        User user = userService.getUser(username);
        System.out.println(user.getUsername() + " " + user.getPassword());
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {

                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority(user.getUserRoles().getRoles().toString()));
                Authentication token = new UsernamePasswordAuthenticationToken(username, null, authorities);
                return token;
            } else {
                throw new BadCredentialsException("Password Mismatch!");
            }
        } else {
            throw new UsernameNotFoundException("Username not found in the system!");
        }
    }
}