package com.uyunxun.mmp.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uyunxun.mmp.domain.pojo.UserDTO;
import com.uyunxun.mmp.security.config.UserAuthentication;
import com.uyunxun.mmp.security.service.TokenAuthenticationService;
import com.uyunxun.mmp.service.UserService;
import com.uyunxun.mmp.util.MD5Util;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by hu on 2017/2/6.
 */
public class StatelessLoginFilter extends AbstractAuthenticationProcessingFilter {
    private final TokenAuthenticationService tokenAuthenticationService;
    private final UserService userService;

    public StatelessLoginFilter(String urlMapping, TokenAuthenticationService tokenAuthenticationService,
                                UserService userService, AuthenticationManager authenticationManager) {
        super(urlMapping);
        this.tokenAuthenticationService = tokenAuthenticationService;
        this.userService = userService;
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
//        Map<String, String> map = new HashMap<String, String>();
//        Enumeration headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String key = (String) headerNames.nextElement();
//            String value = request.getHeader(key);
//            map.put(key, value);
//        }
        String useraccount = request.getParameter("useraccount");
        String password = MD5Util.getMessageDigest(request.getParameter("password").getBytes());
        final UserDTO user = new UserDTO(useraccount,password,"");

//        String jsonString = IOUtils.toString(request.getInputStream());
//        JSONObject userJSON = new JSONObject(jsonString);
//        System.out.format("\n[StatelessLoginFilter.attemptAuthentication] useraccount=%s\n", userJSON.getString("useraccount"));
//        final UserDTO user = toUser(request);
        final UsernamePasswordAuthenticationToken loginToken = user.toAuthenticationToken();
        return getAuthenticationManager().authenticate(loginToken);
    }

    private UserDTO toUser(HttpServletRequest request) throws IOException {
        return new ObjectMapper().readValue(request.getInputStream(), UserDTO.class);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        final UserDetails authenticatedUser = userService.loadUserByUsername(authResult.getName());
        final UserAuthentication userAuthentication = new UserAuthentication(authenticatedUser);
        tokenAuthenticationService.addJwtTokenToHeader(response, userAuthentication);
        SecurityContextHolder.getContext().setAuthentication(userAuthentication);
    }
}
