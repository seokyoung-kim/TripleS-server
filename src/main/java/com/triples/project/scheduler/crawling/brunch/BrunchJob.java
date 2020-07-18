package com.triples.project.scheduler.crawling.brunch;

import java.util.List;

import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.triples.project.dao.ICardDao;
import com.triples.project.dao.collection.Card;
import com.triples.project.scheduler.crawling.ICrawling;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BrunchJob extends QuartzJobBean implements InterruptableJob {

    private final ICardDao cardDao;
    
    @Autowired
    @Qualifier("brunchCrawling")
    private final ICrawling crawling;


    // 예외 처리
    @Override
    public void interrupt() throws UnableToInterruptJobException {

    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        // 크롤링 로직 작성 to do

        List<Card> cardList = crawling.startCrawling();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + cardList.size());
        cardDao.saveAll(cardList);
    }
}
