package com.triples.project.security;


import com.triples.project.dao.collection.User;
import com.triples.project.dto.Role;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

/**
 * OncePerRequestFilter : 같은 요청에 대해서 단 한번만 처리가 수행되는 것을 보장하는 기반 클래스로
 *                        GenericFilterBean을 상속하고 있으며, 스프링 제공 서블릿 필터는 해당 클래스를 상속받아서 구현
 */
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired private TokenProvider tokenProvider;
    @Autowired private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            // Get JWT Token
            String jwt = getJwtFromRequest(request);

            log.info(">>>>>> TokenAuthenticationFilter " + jwt);

            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) { // JWT Validate
                Claims claims = tokenProvider.getClaimsFromToken(jwt);
                String userId = claims.getSubject();
                String email = (String) claims.get("email");
                String role = (String) claims.get("role");
                Collection<GrantedAuthority> authorities =
                        Collections.singleton(new SimpleGrantedAuthority("ROLE_"+role));

                User user = User.builder()
                        .id(userId)
                        .email(email)
                        .role(Role.valueOf(role)).build();


                UsernamePasswordAuthenticationToken authentication
                        = new UsernamePasswordAuthenticationToken(customUserDetailsService.createUserDetails(user),
                        null, authorities);

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 실제 SecurityContext 에 autentication 정보를 등록한다.
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {

            log.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}