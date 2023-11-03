package com.pangjie.springSecurity;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {


    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = httpServletRequest.getHeader(this.tokenHeader);
        if (authHeader != null) {
            if (StringUtils.isNotBlank(authHeader) && authHeader.startsWith(this.tokenHead)) {
                String authToken = authHeader.substring(this.tokenHead.length());
                String username = jwtTokenUtil.getUserNameFromToken(authToken);
                log.info("checking authentication " + username);
                if (StringUtils.isNotBlank(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                    // 校验token
                    if (jwtTokenUtil.validateToken(authToken)) {

                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userDetails.getUsername(), "******", null);
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                                httpServletRequest));
                        log.info("authenticated user " + username + ", setting security context");
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        }
        //直接从redis中获取token 并验证
//        else {
//            String token = "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiI2NGFkYjQxOWViMjA0MzliYTgwYmQzYjU1ZGNlNWQwMCIsImF1dGgiOiJhZG1pbiIsInN1YiI6ImFkbWluIn0.IfxEUd_wnU8Q4Cr5adU-llfIsmztypQGUlsqxmhLjcY6dA4UIAStwVvCid4kaQdxNkX3f2AXmp_XnIsvWUXhZg";
//            stringRedisTemplate.opsForValue().get("online-token-" +
//                    token);
//            String username = jwtTokenUtil.getUserNameFromToken(token);
//            UserInfo userInfo = new UserInfo();
//            userInfo.setUserName(username);
//            JwtUser jwtUser = new JwtUser(userInfo);
//            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                    jwtUser, null, null);
//            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
//                    httpServletRequest));
//            log.info("authenticated user " + username + ", setting security context");
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}