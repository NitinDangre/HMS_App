package com.hms.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.sql.Date;

@Service
public class JWTServices {

    @Value("${jwt.algorithm.key}")
    private String algorithmKey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiry.duration}")
    private Long expiryTime;

    private Algorithm algorithm;
    @PostConstruct
    public void postConstruct() throws UnsupportedEncodingException {
        algorithm= Algorithm.HMAC256(algorithmKey);
    }
    //generate JWT token to get Username From Token
    public String generateToken(String userName){
       return  JWT.create()
                .withClaim("name",userName)
                .withExpiresAt(new Date(System.currentTimeMillis()+expiryTime))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    //Verify JWT token to get Username From Token
    public String getUserName(String  token){
        DecodedJWT decodedJwt=JWT.require(algorithm)
                                .withIssuer(issuer)
                                .build()
                                .verify(token);
        return decodedJwt.getClaim("name").asString();
    }
}
