package com.uyunxun.mmp.security.service;

import com.uyunxun.mmp.security.config.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by hu on 2017/2/6.
 */
@Service
public class TokenAuthenticationService {
    private static final String AUTH_HEADER_NAME = "x-auth-token";

    private final JwtTokenHandler jwtTokenHandler;

    @Autowired
    public TokenAuthenticationService(JwtTokenHandler jwtTokenHandler) {
        this.jwtTokenHandler = jwtTokenHandler;
    }

    public String addJwtTokenToHeader(HttpServletResponse response,
                                    UserAuthentication authentication) {
        final UserDetails user = authentication.getDetails();
        String token = jwtTokenHandler.createTokenForUser(user);
        response.addHeader(AUTH_HEADER_NAME, token);
        response.setContentType("application/json");
        try {
            PrintWriter out = response.getWriter();
            out.println("{\"token\": \""+token + "\"}");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return token;
    }

    public Authentication generateAuthenticationFromRequest(HttpServletRequest request) {
        final String token = request.getHeader(AUTH_HEADER_NAME);
        if (token == null || token.isEmpty()) return null;
        return jwtTokenHandler
                .parseUserFromToken(token)
                .map(UserAuthentication::new)
                .orElse(null);
    }
}
