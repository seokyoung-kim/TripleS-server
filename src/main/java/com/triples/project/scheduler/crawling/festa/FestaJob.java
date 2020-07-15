package com.triples.project.scheduler.crawling.festa;

import com.triples.project.dao.ICardDao;
import com.triples.project.dao.collection.Card;
import com.triples.project.scheduler.crawling.ICrawling;
import lombok.RequiredArgsConstructor;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 */

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
        // 크롤링 로직 작성 to do

        List<Card> cardList = crawling.startCrawling();

        cardDao.saveAll(cardList);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + cardList.size());

    }
}
