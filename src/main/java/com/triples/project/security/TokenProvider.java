package com.triples.project.security;

import com.triples.project.configuration.oauth.AppProperties;
import io.jsonwebtoken.*;
import javafx.scene.control.Alert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Slf4j
@RequiredArgsConstructor
@Service
public class TokenProvider {

    private final AppProperties appProperties;

    public String createToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        log.info("CreateToken : " + authentication.toString());

        return Jwts.builder()
                .setSubject(userPrincipal.getId())
                .claim("username", userPrincipal.getUsername()) ///////// 확인 해보기
                .claim("email", userPrincipal.getEmail())
                .claim("role", userPrincipal.getRole().name())
                .setIssuedAt(new Date(System.currentTimeMillis()))                // 토큰 생성일
                .setExpiration(new Date(System.currentTimeMillis() +
                        appProperties.getAuth().getTokenExpirationMsec() * 1000)) // 토큰 만료일
                .signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
                .compact();
    }
    // token parsing
    public Claims getClaimsFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(appProperties.getAuth().getTokenSecret())
                .parseClaimsJws(token)
                .getBody();

        return claims;
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(appProperties.getAuth().getTokenSecret()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch(MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch(ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch(UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch(IllegalArgumentException ex) {
            log.error("JWT claims string is empty");
        }
        return false;
    }
}
