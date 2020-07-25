  
package com.triples.project.scheduler.crawling.velog;

import com.triples.project.dao.ICardDao;
import com.triples.project.dao.collection.Card;
import com.triples.project.scheduler.crawling.ICrawling;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class VelogJob extends QuartzJobBean implements InterruptableJob {

    private final ICardDao cardDao;
    @Qualifier("velogCrawling")
    @Autowired
    private final ICrawling velogCrawling;

    // 예외 처리
    @Override
    public void interrupt() throws UnableToInterruptJobException {

    }

    @SneakyThrows
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        // 크롤링 로직 작성 to do

        List<Card> cardList = velogCrawling.startCrawling();

        cardDao.saveAll(cardList);

        System.out.println("Velog >>>>>>>>>>>>>>>>>>>>>>>>>>>>" + cardList.size());

    }
}