package com.microservice.apiGateway.jwtHandler;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.microservice.apiGateway.utils.AppConstants;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class JwtHandler {

    @Value("${jwt.secret}")
    private String secret;

    public boolean isTokenValid(@NotNull String username, String token){
        JWTVerifier verifier = getJWTVerifier();
        return !username.isEmpty()&& !isTokenExpired(verifier,token);
    }
    public String getSubject(String token){
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token).getSubject();
    }
    private boolean isTokenExpired(@NotNull JWTVerifier verifier, String token) {
        Date expiresAt = verifier.verify(token).getExpiresAt();
        return expiresAt.before(new Date());
    }
    private String[] getClaimsFromToken(String token) {
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token)
                .getClaim(AppConstants.AUTHORITIES)
                .asArray(String.class);
    }
    private JWTVerifier getJWTVerifier() {
        JWTVerifier verifier;
        try {
            Algorithm algorithm = Algorithm.HMAC512(secret);
            verifier = JWT.require(algorithm).withIssuer(AppConstants.GET_ARRAYS_LLC).build();
            return verifier;
        }
        catch (JWTVerificationException exception){
            throw new JWTVerificationException(AppConstants.TOKEN_CANNOT_BE_VERIFIED);
        }
    }
}
