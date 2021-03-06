package com.myTrade.jwt;

import com.google.common.base.Strings;
import com.myTrade.utility.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public final class JwtTokenVerifier extends OncePerRequestFilter {
    private final JwtSecretKey jwtSecretKey;
    private final JwtConfiguration jwtConfiguration;
    private final JwtUtility jwtUtility;

    @Autowired
    public JwtTokenVerifier(JwtConfiguration jwtConfiguration, JwtSecretKey jwtSecretKey) {
        this.jwtSecretKey = jwtSecretKey;
        this.jwtConfiguration = jwtConfiguration;
        this.jwtUtility = new JwtUtility(this.jwtConfiguration, this.jwtSecretKey);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

            String authorizationCookie = httpServletRequest.getHeader(jwtConfiguration.getCookieHeader());
            if (Strings.isNullOrEmpty(authorizationCookie) || !authorizationCookie.startsWith(jwtConfiguration.getAuthTokenPrefixCookie())) {
                filterChain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }
            String token = jwtUtility.prepareAuthToken(authorizationCookie);
            Authentication authentication = jwtUtility.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
