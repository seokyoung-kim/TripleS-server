package com.triples.project.dto;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
// Spring Security 3.2 부터 annotaion을 이용하여 현재 로그인한 사용자 객체를 인자에 주입 가능
// @AuthenticationPrincipal 을 사용하여 SecurityContextHolder.getContext().getAuthentication 처럼 사용가능
@AuthenticationPrincipal
public @interface CurrentUser {

}