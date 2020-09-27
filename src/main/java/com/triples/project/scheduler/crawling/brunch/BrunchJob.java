package com.triples.project.scheduler.crawling.brunch;

import java.time.LocalDateTime;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.triples.project.dao.ICardDao;
import com.triples.project.dao.collection.Card;
import com.triples.project.scheduler.crawling.ICrawling;

import lombok.RequiredArgsConstructor;

@Slf4j
@RequiredArgsConstructor
public class BrunchJob extends QuartzJobBean implements InterruptableJob {

    private final ICardDao cardDao;
    
    private final ICrawling brunchCrawling;

    private boolean isInterrupted = false;
    private JobKey jobKey = null;

    // 예외 처리
    @Override
    public void interrupt() throws UnableToInterruptJobException {

    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        jobKey = context.getJobDetail().getKey();

        // 현재 JobName 확인
        log.info("### {} is being executed!",
                context.getJobDetail().getJobDataMap().get("JobName").toString());

        List<Card> cardList = null;
		try {
			cardList = brunchCrawling.startCrawling();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        cardDao.saveAll(cardList);

        log.info("execute invoked, jobKey: " + jobKey + ", time:" +
                LocalDateTime.now().toString() + ", crawling size : " + cardList.size());
    }
}
