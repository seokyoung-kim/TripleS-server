package com.triples.project.scheduler.crawling.hello;

import com.triples.project.dao.ICardDao;
import com.triples.project.scheduler.crawling.ICrawling;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;

@Slf4j
public class HelloJob extends BaseJob {

    @Override
    public void doExecute(JobExecutionContext context) {
        log.info("### {} is being executed!",
                context.getJobDetail().getJobDataMap().get("JobName").toString());

    }

}
