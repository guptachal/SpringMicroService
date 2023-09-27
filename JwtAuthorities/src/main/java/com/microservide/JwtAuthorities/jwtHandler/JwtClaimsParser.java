package com.microservide.JwtAuthorities.jwtHandler;

import io.jsonwebtoken.*;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;
import java.util.stream.Collectors;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class JwtClaimsParser {
        Jwt jwtObject;
    public JwtClaimsParser(String jwt, String secret){
        this.jwtObject = parseJwt(jwt, secret);
    }

    private Jwt<?,?> parseJwt(String jwt, String secret) {
        byte[] secretKeyByte = Base64.getEncoder().encode(secret.getBytes());
        SecretKey secretKey = new SecretKeySpec(secretKeyByte, SignatureAlgorithm.HS512.getJcaName());
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build();
        return parser.parse(jwt);
    }
    public Collection<? extends GrantedAuthority> getAuthorities(){
        List<String> roles = ((Claims)jwtObject.getBody()).get("Authorities", List.class);
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
    }

    public String getSubject(){
        return ((Claims)jwtObject.getBody()).getSubject();
    }
}