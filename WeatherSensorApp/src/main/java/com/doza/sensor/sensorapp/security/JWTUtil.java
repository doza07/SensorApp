package com.doza.sensor.sensorapp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt_secret}")
    private String secret;
    @Value("${jwt_lifetime}")
    private int time;

    public String generateToken(String username) {
        Date expDate = Date.from(ZonedDateTime.now().plusMinutes(time).toInstant());

        return JWT.create()
                .withSubject("User details")
                .withClaim("username", username)
                .withIssuedAt(new Date())
                .withIssuer("doza.app")
                .withExpiresAt(expDate)
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndRetrieveClaim(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User details")
                .withIssuer("doza.app")
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("username").asString();
    }
}
