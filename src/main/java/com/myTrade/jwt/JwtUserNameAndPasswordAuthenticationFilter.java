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

public class JwtUserNameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

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

        //TODO: [Q] Is it better to check whole code in try-catch clause?
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {




        String authToken = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
//                .setIssuedAt(new Date())   //TODO:[Q] Unnecessary param?
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfiguration.getTokenExpirationAfterDays())))
                .signWith(jwtSecretKey.secretKey())
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(authResult.getName())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfiguration.getTokenExpirationAfterDays())))
                .signWith(jwtSecretKey.secretKey())
                .compact();

        Cookie authCookie = new Cookie("authToken", authToken);
        authCookie.setMaxAge(7 * 24 * 60 * 60); // expires in 7 days
        authCookie.setSecure(true);
        authCookie.setHttpOnly(true);

        Cookie refreshCookie = new Cookie("refreshToken", refreshToken ); //TODO[Q] Do i need refreshToken? (while i'm storing authToken in httpOnly)
        refreshCookie.setMaxAge(7 * 24 * 60 * 60); // expires in 7 days
        refreshCookie.setSecure(true);
        refreshCookie.setHttpOnly(true);

        response.addCookie(authCookie);
        response.addCookie(refreshCookie);
        response.addHeader(HttpHeaders.AUTHORIZATION, authResult.getName()); //TODO:[Q] How to send username to webpage? (for context purpose)
    }
}
