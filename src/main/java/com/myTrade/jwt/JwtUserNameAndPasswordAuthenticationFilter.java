package com.myTrade.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

import static com.myTrade.utility.JwtUtility.*;

public final class JwtUserNameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtConfiguration jwtConfiguration;
    private final JwtSecretKey jwtSecretKey;

    public JwtUserNameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager, JwtConfiguration jwtConfiguration, JwtSecretKey jwtSecretKey) {
        this.authenticationManager = authenticationManager;
        this.jwtConfiguration = jwtConfiguration;
        this.jwtSecretKey = jwtSecretKey;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        LoginCredentials loginCredentials = null;
        try {
            loginCredentials = new ObjectMapper().readValue(request.getInputStream(), LoginCredentials.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginCredentials.getUsername(), loginCredentials.getPassword());
        return authenticationManager.authenticate(authentication);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        String authToken = Jwts.builder()
                .setSubject(authResult.getName())
                .claim(AUTHORITIES_NAME_VALUE, authResult.getAuthorities())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfiguration.getTokenExpirationAfterDays())))
                .signWith(jwtSecretKey.secretKey())
                .compact();

        Cookie authCookie = new Cookie(AUTH_TOKEN_NAME_VALUE, authToken);
        authCookie.setMaxAge(COOKIE_LIFETIME_IN_SECONDS);
        authCookie.setSecure(true);
        authCookie.setHttpOnly(true);

        response.addCookie(authCookie);
        response.addHeader(HttpHeaders.AUTHORIZATION, authResult.getName());
    }


}
