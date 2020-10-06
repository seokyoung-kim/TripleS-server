  
package com.triples.project.scheduler.crawling.velog;

import java.time.LocalDateTime;
import java.util.List;

import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.UnableToInterruptJobException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.triples.project.dao.ICardDao;
import com.triples.project.dao.collection.Card;
import com.triples.project.scheduler.crawling.ICrawling;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class VelogJob extends QuartzJobBean implements InterruptableJob {

    private final ICardDao cardDao;
    private final ICrawling velogCrawling;

    private JobKey jobKey = null;

    // 예외 처리
    @Override
    public void interrupt() throws UnableToInterruptJobException {

    }

    @SneakyThrows
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        jobKey = context.getJobDetail().getKey();

        // 현재 JobName 확인
        log.info("### {} is being executed!",
                context.getJobDetail().getJobDataMap().get("JobName").toString());

        List<Card> cardList = velogCrawling.startCrawling();

        cardDao.saveAll(cardList);

        log.info("execute invoked, jobKey: " + jobKey + ", time:" +
                LocalDateTime.now().toString() + ", crawling size : " + cardList.size());

    }
}