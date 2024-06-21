package it.capstone.arno.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import it.capstone.arno.exception.UnauthorizedException;
import it.capstone.arno.model.Utente;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTool {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.duration}")
    private long duration;


    private String userToken;

    private synchronized void setUserToken(String userToken) {
        this.userToken = userToken;
    }


    public synchronized String getUserToken() {
        return userToken;
    }


    public String createToken(Utente user) {
        String newToken = Jwts.builder().
                issuedAt(new Date(System.currentTimeMillis())).
                expiration(new Date(System.currentTimeMillis() + duration)).
                subject(String.valueOf(user.getId())).
                signWith(Keys.hmacShaKeyFor(secret.getBytes())).
                compact();

        setUserToken(newToken);
        System.out.println(newToken);

        return newToken;
    }


    public void verifyToken(String token) {
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).
                    build().parse(token);
        } catch (Exception e) {
            throw new UnauthorizedException("Failed to authorize. Please login again to access this resource.");
        }
    }


    public int getIdFromToken(String token) {
        return Integer.parseInt(Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject());
    }

}
