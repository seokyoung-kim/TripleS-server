package com.triples.project.controller;

import com.triples.project.dao.IUserDao;
import com.triples.project.dao.collection.User;
import com.triples.project.dto.CurrentUser;
import com.triples.project.exception.ResourceNotFoundException;
import com.triples.project.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserApiController {


    private final IUserDao iUserDao;

    /**
     * 유저 프로필 보기
     */
    @GetMapping("/api/v1/user/profile")
    @PreAuthorize("hasRole('GUEST')")   // 추 후 변경 후 수정
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {

        log.info("getCurrentUser : " + userPrincipal.toString());

        return iUserDao.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }
}
