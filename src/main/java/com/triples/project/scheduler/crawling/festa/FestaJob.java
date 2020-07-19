package com.triples.project.scheduler.crawling.festa;

import com.triples.project.dao.ICardDao;
import com.triples.project.dao.collection.Card;
import com.triples.project.scheduler.crawling.ICrawling;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class FestaJob extends QuartzJobBean implements InterruptableJob {

    private final ICardDao cardDao;
    private final ICrawling crawling;


    // 예외 처리
    @Override
    public void interrupt() throws UnableToInterruptJobException {

    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        // 현재 JobName 확인
        log.info("### {} is being executed!",
                context.getJobDetail().getJobDataMap().get("JobName").toString());

        List<Card> cardList = new ArrayList<>();
        try {
            cardList = crawling.startCrawling();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        cardDao.saveAll(cardList);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + cardList.size());

    }
}
