package com.triples.project;

import com.triples.project.scheduler.crawling.CrawlingScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RequiredArgsConstructor
@SpringBootApplication
public class ProjectApplication {


    private final CrawlingScheduler scheduler; // 스케줄러

    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class, args);
    }

}
