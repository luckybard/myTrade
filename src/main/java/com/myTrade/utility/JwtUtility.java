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

    private final JwtConfiguration jwtConfiguration;
    private final JwtSecretKey jwtSecretKey;

    @Autowired
    public JwtUtility(JwtConfiguration jwtConfiguration, JwtSecretKey jwtSecretKey) {
        this.jwtConfiguration = jwtConfiguration;
        this.jwtSecretKey = jwtSecretKey;
    }

    public String prepareAuthToken(String authorizationCookie) {
        String token = authorizationCookie.replace(jwtConfiguration.getAuthTokenPrefixCookie(), "");
        int semiColonIndex = token.indexOf(";");
        token = token.substring(0, semiColonIndex);
        return token;
    }

    public Authentication getAuthentication(String token){
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(jwtSecretKey.secretKey())
                    .build()
                    .parseClaimsJws(token);
            Claims claimsJwsBody = claimsJws.getBody();
            String userName = claimsJwsBody.getSubject();
            List<Map<String, String>> authorities = (List<Map<String, String>>) claimsJwsBody.get("authorities");
            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream().map(m -> new SimpleGrantedAuthority(m.get("authority")))
                    .collect(Collectors.toSet());
            return new UsernamePasswordAuthenticationToken(userName, null, simpleGrantedAuthorities);
        } catch (
                JwtException e) {
            throw new IllegalStateException(String.format("Token %s cannot be trusted", token + e));
        }
    }
}
