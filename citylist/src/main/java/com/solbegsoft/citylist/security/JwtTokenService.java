package com.solbegsoft.citylist.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * Service for jwt token
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenService implements TokenService{

    /**
     * Bearer prefix
     */
    public static final String BEARER_PREFIX = "Bearer ";

    /**
     * Token secret
     */
    @Value("${token.secret}")
    private String key;

    /**
     * Create token from username
     *
     * @param userName user name
     * @return token
     */
    @Override
    public String createToken(String userName) {

        return Jwts.builder()
                .setSubject(userName)
                .setExpiration(Date.from(LocalDateTime.now().plusMinutes(30).toInstant(ZoneOffset.UTC)))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    /**
     * Validate token and return user name, can return null when token invalid
     *
     * @param token token
     * @return user name
     */
    @Override
    @Nullable
    public String validateTokenAndGetUsername(String token) {

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(key))
                    .parseClaimsJws(token).getBody();
            Date expiration = claims.getExpiration();
            if (expiration.before(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)))) {
                return null;
            }
            return claims.getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Create token with bearer prefix
     *
     * @param rawToken raw token
     * @return token with prefix
     */
    @Override
    public String createBearer(String rawToken) {

        return BEARER_PREFIX + rawToken;
    }
}
