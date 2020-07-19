package com.triples.project.scheduler.crawling.hello;

import com.triples.project.dao.ICardDao;
import com.triples.project.scheduler.crawling.ICrawling;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;

@Slf4j
public abstract class BaseJob extends QuartzJobBean implements InterruptableJob {

    // 예외 처리
    @Override
    public void interrupt() throws UnableToInterruptJobException {

    }

    @Override
    public void executeInternal(JobExecutionContext context) throws JobExecutionException {
        beforeExecute(context);
        doExecute(context);
        afterExecute(context);
        scheduleNextJob(context);
    }

    private void beforeExecute(JobExecutionContext context) {
        log.info("%%% Before executing job");
    }

    protected abstract void doExecute(JobExecutionContext context);

    private void afterExecute(JobExecutionContext context) {

        log.info("%%% After executing job");
        Object object = context.getJobDetail().getJobDataMap().get("JobDetailQueue");
        List<JobDetail> jobDetailQueue = (List<JobDetail>) object;

        if (jobDetailQueue.size() > 0) {
            jobDetailQueue.remove(0);
        }
    }

    private void scheduleNextJob(JobExecutionContext context) {

        log.info("$$$ Schedule Next Job");

        Object object = context.getJobDetail().getJobDataMap().get("JobDetailQueue");
        List<JobDetail> jobDetailQueue = (List<JobDetail>) object;

        if (jobDetailQueue.size() > 0) {
            JobDetail nextJobDetail = jobDetailQueue.get(0);
            nextJobDetail.getJobDataMap().put("JobDetailQueue", jobDetailQueue);

            Trigger trigger = TriggerBuilder.newTrigger().
                withSchedule(CronScheduleBuilder.cronSchedule("5 * * * * ?")).build();

            try {
                // 아래의 팩토리 메서드는 이름이 같으면 여러번 호출해도 항상 동일한 스케줄러를 반환한다.
                Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
                scheduler.start();
                scheduler.scheduleJob(nextJobDetail, trigger);
            } catch (SchedulerException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
