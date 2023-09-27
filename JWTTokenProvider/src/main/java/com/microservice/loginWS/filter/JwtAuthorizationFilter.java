package com.microservice.loginWS.filter;

import com.microservice.loginWS.jwtProvider.JwtTokenProvider;
import static com.microservice.loginWS.utils.AppConstants.*;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private JwtTokenProvider jwtTokenProvider;

    public JwtAuthorizationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if(request.getMethod().equalsIgnoreCase(OPTIONS_HTTP_METHOD)){
            response.setStatus(HttpStatus.OK.value());
        }
        else {
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if(authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_PREFIX)){
                filterChain.doFilter(request,response);
                return;
            }
            String token = authorizationHeader.substring(TOKEN_PREFIX.length());
            String username = jwtTokenProvider.getSubject(token);

            if(jwtTokenProvider.isTokenValid(username,token) &&
                    SecurityContextHolder.getContext().getAuthentication() == null){
                List<GrantedAuthority> authorities = jwtTokenProvider.getAuthorities(token);

                System.out.println(authorities.toString());
                Authentication authentication = jwtTokenProvider.getAuthentication(username, authorities,request);
                System.out.println("ENTERING INTO THE CONTEXT HOLDER");
                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("ENTERED INTO THE CONTEXT HOLDER");
            }
            else {
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(request,response);
    }
}