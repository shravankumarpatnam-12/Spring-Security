package com.spring.security.springsecurityexample.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    @Value("${spring.app.jwtExpirationInMilliseconds}")
    private int jwtExpirationInMilliseconds;
    @Value("${spring.app.JwtSecret}")
    private String jwtSecret;

    public String getJWTfromHeader(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        logger.debug("Authorization : {}", token);
        if(token != null && token.startsWith("Bearer ")){
            return token.substring(7);
        }
        return  null;
    }

    public String generateTokenFromUsername(UserDetails userDetails){
        String userName=userDetails.getUsername();
        return Jwts.builder()
                .subject(userName)
                .issuedAt(new Date())
                .expiration(new Date((new Date().getTime()+jwtExpirationInMilliseconds)))
                .signWith((SecretKey)key())
                .compact();
    }

    public String getUsernameFromToken(String token){
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build().parseSignedClaims(token)
                .getPayload().getSubject();
    }

    public Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith((SecretKey) key()).build().parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token : {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token : {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token : {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty : {}", e.getMessage());
        }
        return false;
    }
}
