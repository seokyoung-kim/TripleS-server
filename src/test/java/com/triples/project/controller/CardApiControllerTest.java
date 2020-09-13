package com.triples.project.controller;


import com.triples.project.dao.collection.Card;
import com.triples.project.dto.CursorResult;
import com.triples.project.service.CardService;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CardApiControllerTest {

    @LocalServerPort private int port;

    @Autowired private CardService cardService;
    @Autowired private TestRestTemplate restTemplate;

    @Test
    public void findAllByOrderByIdDesc() {
        // given
        CursorResult<Card> list = cardService.findAllByOrderByIdDesc(null, PageRequest.of(0,20));
        ObjectId id = list.getCursorId();
        String url = "http://localhost:"+port+"/api/v1/cards?cursorId="+id+"&pageSize="+40;

        //when
        ResponseEntity<String> responseEntity =
                restTemplate.getForEntity(url, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("해당 플랫폼의 카드를 출력한다.")
    public void findByPlatform() {

        //given
        String url = "http://localhost:"+port+"/api/v1/cards/platform/festa";

        //when
        ResponseEntity<String> responseEntity =
                restTemplate.getForEntity(url, String.class);
        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("해당 카테고리의 카드를 출력한다.")
    public void findByCategory() {

        //given
        String url = "http://localhost:"+port+"/api/v1/cards/category/spring boot";

        //when
        ResponseEntity<String> responseEntity =
                restTemplate.getForEntity(url, String.class);
        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("해당 작성자의 카드를 출력한다")
    public void findByWriter() {
        //given
        String url = "http://localhost:"+port+"/api/v1/cards/writer/백명석";

        //when
        ResponseEntity<String> responseEntity =
                restTemplate.getForEntity(url, String.class);

        System.out.println(">>>>>>>>> "+responseEntity.getBody());
        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}