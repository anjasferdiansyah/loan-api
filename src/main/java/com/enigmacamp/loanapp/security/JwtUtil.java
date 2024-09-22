package com.enigmacamp.loanapp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.enigmacamp.loanapp.entity.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtUtil {

    @Value("${app.warung-makan-bahari.jwt-secret}")
    private  String jwtSecret;

    @Value("${app.warung-makan-bahari.app-name}")
    private String appName;

    @Value("${app.warung-makan-bahari.jwtExpirationTimeInSecond}")
    private long jwtExpirationTimeInSecond;

    public String generateToken(AppUser user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());

            List<String> roleNames = user.getRoles().stream()
                    .map((role -> role.getRole().name()))  // Assuming Role has a getName() method
                    .collect(Collectors.toList());
            String token = JWT.create()
                    .withIssuer(appName)
                    .withSubject(user.getId())
                    .withExpiresAt(Instant.now().plusSeconds(jwtExpirationTimeInSecond))
                    .withIssuedAt(Instant.now())
                    .withClaim("role", roleNames)
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception){
            log.error("Error while creating JWT {}", exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }
    }


    public boolean verifyJwtToken(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());

            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            log.info("decoded JWT :" + decodedJWT.toString());
            return decodedJWT.getIssuer().equals(appName);
        } catch (JWTVerificationException exception){
            log.error("Error while verifying JWT {}", exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }
    }

    public Map<String, Object> getUserInfoByToken(String token) {

        try{
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());

            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);

            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("userId", decodedJWT.getSubject());
            userInfo.put("role", decodedJWT.getClaim("role"));

            return userInfo;
        } catch (JWTVerificationException exception){
            log.error("Error while verifying token {}", exception.getMessage());
            return null;
        }

    }
}