package com.triples.project;

import com.triples.project.configuration.oauth.AppProperties;
import com.triples.project.scheduler.crawling.CrawlingScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@RequiredArgsConstructor
@EnableConfigurationProperties(AppProperties.class)
@SpringBootApplication
public class ProjectApplication {

    private final CrawlingScheduler scheduler; // 스케줄러

    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class, args);
    }

}
