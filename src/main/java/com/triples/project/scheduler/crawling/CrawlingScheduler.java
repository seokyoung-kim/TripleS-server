package com.triples.project.scheduler.crawling;

import com.triples.project.scheduler.crawling.festa.FestaJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 *  스케줄러
 */
@Component
@RequiredArgsConstructor
public class CrawlingScheduler {

    private final Scheduler scheduler;

    @PostConstruct
    public void start() throws SchedulerException {

        //job 지정
        JobDetail job = JobBuilder.newJob(FestaJob.class).withIdentity("festa").build();
        //JobDetail job2 = JobBuilder.newJob(FestaJob2.class).withIdentity("festa2").build();

        //trigger 생성
        Trigger trigger = TriggerBuilder.newTrigger().
                withSchedule(CronScheduleBuilder.cronSchedule("5 * * * * ?")).build();
//        startAt과 endAt을 사용해 job 스케쥴의 시작, 종료 시간도 지정할 수 있다.
//        Trigger trigger = TriggerBuilder.newTrigger().startAt(startDateTime).endAt(EndDateTime)
//                .withSchedule(CronScheduleBuilder.cronSchedule("*/1 * * * *")).build();

//        Trigger trigger2 = TriggerBuilder.newTrigger().
//                withSchedule(CronScheduleBuilder.cronSchedule("5 * * * * ?")).build();

        scheduler.scheduleJob(job, trigger);
        //scheduler.scheduleJob(job2, trigger2);
    }
}
