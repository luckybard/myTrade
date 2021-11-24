package com.myTrade.utility;

import com.myTrade.jwt.JwtConfiguration;
import com.myTrade.jwt.JwtSecretKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtUtility {
    public static final int COOKIE_LIFETIME_IN_SECONDS = 604800;
    public static final String AUTH_TOKEN_NAME_VALUE = "authToken";
    public static final String AUTHORITIES_NAME_VALUE = "authorities";
    public static final String EMPTY_STRING = "";
    public static final String AUTHORITY_NAME_VALUE = "authority";

    private final JwtConfiguration jwtConfiguration;
    private final JwtSecretKey jwtSecretKey;

    @Autowired
    public JwtUtility(JwtConfiguration jwtConfiguration, JwtSecretKey jwtSecretKey) {
        this.jwtConfiguration = jwtConfiguration;
        this.jwtSecretKey = jwtSecretKey;
    }

    public String prepareAuthToken(String authorizationCookie) {
        return authorizationCookie.replace(jwtConfiguration.getAuthTokenPrefixCookie(), EMPTY_STRING);
    }

    public Authentication getAuthentication(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(jwtSecretKey.secretKey())
                    .build()
                    .parseClaimsJws(token);
            Claims claimsJwsBody = claimsJws.getBody();
            String userName = claimsJwsBody.getSubject();
            List<Map<String, String>> authorities = (List<Map<String, String>>) claimsJwsBody.get(AUTHORITIES_NAME_VALUE);
            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream().map(m -> new SimpleGrantedAuthority(m.get(AUTHORITY_NAME_VALUE)))
                    .collect(Collectors.toSet());
            return new UsernamePasswordAuthenticationToken(userName, null, simpleGrantedAuthorities);
        } catch (
                JwtException e) {
            throw new IllegalStateException(String.format("Token %s cannot be trusted", token + e));
        }
    }
}
