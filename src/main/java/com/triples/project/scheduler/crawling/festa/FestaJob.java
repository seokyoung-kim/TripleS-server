package com.triples.project.scheduler.crawling.festa;

import com.triples.project.dao.ICardDao;
import com.triples.project.dao.collection.Card;
import com.triples.project.scheduler.crawling.ICrawling;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.UnableToInterruptJobException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */

@Slf4j
@RequiredArgsConstructor
public class FestaJob extends QuartzJobBean implements InterruptableJob {

    private final ICardDao cardDao;
    private final ICrawling festaCrawling;

    private boolean isInterrupted = false;
    private JobKey jobKey = null;

    // 예외 처리
    @Override
    public void interrupt() throws UnableToInterruptJobException {
        log.info(jobKey + "  -- INTERRUPTING --");
        isInterrupted = true;
    }

    @SneakyThrows
    @Override
    protected void executeInternal(JobExecutionContext context)  {
        jobKey = context.getJobDetail().getKey();
        if (isInterrupted) {
            log.warn("jobKey: " + jobKey + "is Interrupted.");
            return;
        }
        // 현재 JobName 확인
        log.info("### {} is being executed!",
                context.getJobDetail().getJobDataMap().get("JobName").toString());

        // Crawling Start
        List<Card> cardList = new ArrayList<>();

        cardList = festaCrawling.startCrawling();

        cardDao.saveAll(cardList); // to do : 중복 데이터 제거를 위한 merge 이용할 것

        // Crawling End

        log.info("execute invoked, jobKey: " + jobKey + ", time:" +
                LocalDateTime.now().toString() + ", crawling size : " + cardList.size());
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + cardList.size());

    }
}
