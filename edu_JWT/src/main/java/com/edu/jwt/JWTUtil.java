package com.edu.jwt;

import java.util.Date;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTUtil {

	private final SecretKey secretKey;

	public JWTUtil(@Value("${jwt.secretKey}") String secretKeyString) throws Exception {
		this.secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes("UTF-8"));
	}

	public String generateToken(String username, String role, Long expiredMs) {

		return Jwts.builder()
				.claim("username", username)
				.claim("role", role)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + expiredMs))
				.signWith(secretKey)
				.compact();
	}


    public String getUsername(String token) {

        return Jwts.parser().verifyWith(secretKey).build()
        		.parseSignedClaims(token)
        		.getPayload()
        		.get("username", String.class);
    }

    public String getRole(String token) {

        return Jwts.parser().verifyWith(secretKey).build()
        		.parseSignedClaims(token)
        		.getPayload()
        		.get("role", String.class);
    }

    public Boolean isExpired(String token) {

        return Jwts.parser().verifyWith(secretKey).build()
        		.parseSignedClaims(token)
        		.getPayload()
        		.getExpiration()
        		.before(new Date());
    }
}
