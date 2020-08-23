package com.triples.project.dao;

import com.triples.project.dao.collection.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class IUserDaoTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IUserDao iUserDao;

    @Test
    public void saveTestUser() {

        String testEmail = "test@naver.com";

        String testEmail2 = "test2@naver.com";

        // Role 작명규칙은 반드시 prefix로 ROLE_  을 명시해야 함!
        iUserDao.save(User.builder()
                .username("WonYong")
                .email(testEmail)
                .password(passwordEncoder.encode("test"))
                .role("ROLE_ADMIN")
                .active("1")
                .build());

        iUserDao.save(User.builder().username("WonYong2").email(testEmail2)
                .password(passwordEncoder.encode("test2")).role("ROLE_MANAGER").active("1").build());

        User user = iUserDao.findByEmail(testEmail).orElseThrow(() -> new UsernameNotFoundException("not find"));

        assertThat(user.getEmail()).isEqualTo(testEmail);
    }
}