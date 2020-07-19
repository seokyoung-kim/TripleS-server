package com.triples.project.scheduler.crawling;

import com.triples.project.scheduler.crawling.festa.FestaJob;
import com.triples.project.scheduler.crawling.hello.HelloJob;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;


/**
 *
 * @description : Job 을 정해진 시간에 실행 하도록 설정 ( Crawling Scheduler )
 */
@Component
@RequiredArgsConstructor
public class CrawlingScheduler {

    private final Scheduler scheduler;

    @PostConstruct
    public void start() throws SchedulerException {

        //job 지정
        JobDataMap jobDataMap1 = new JobDataMap();
        jobDataMap1.put("JobName","Job Chain 1"); // JobName 을 Job Chain 1 로 지정
        JobDetail jobDetail = JobBuilder.newJob(FestaJob.class).usingJobData(jobDataMap1).build();

//        JobDataMap jobDataMap2 = new JobDataMap();
//        jobDataMap2.put("JobName","Job Chain 2");
//        JobDetail jobDetail2 = JobBuilder.newJob(HelloJob.class).usingJobData(jobDataMap2).build();
//
//        JobDataMap jobDataMap3 = new JobDataMap();
//        jobDataMap3.put("JobName","Job Chain 3");
//        JobDetail jobDetail3 = JobBuilder.newJob(HelloJob.class).usingJobData(jobDataMap3).build();


//        List<JobDetail> jobDetailQueue = new LinkedList<>();
//        jobDetailQueue.add(jobDetail);
//        jobDetailQueue.add(jobDetail2);
//        jobDetailQueue.add(jobDetail3);
//
//        jobDetail.getJobDataMap().put("JobDetailQueue", jobDetailQueue);


//        //trigger 생성
//        Trigger trigger = TriggerBuilder.newTrigger().
//                withSchedule(CronScheduleBuilder.cronSchedule("5 * * * * ?")).build();
//
//        // 스케줄러 실행 및 JobDetail과 Trigger 정보로 스케줄링
//        Scheduler defaultScheduler = StdSchedulerFactory.getDefaultScheduler();
//        defaultScheduler.start();
//        defaultScheduler.scheduleJob(jobDetail, trigger);



        scheduler.scheduleJob(jobDetail, buildCronJobTrigger("3 * * * * ?"));
//        scheduler.scheduleJob(jobDetail2, buildCronJobTrigger("3 * * * * ?"));
//        scheduler.scheduleJob(jobDetail3, buildCronJobTrigger("3 * * * * ?"));



    }

    /**
     * @param scheduleExp ( cron 표현식 )
     * @Description Trigger 생성 함수
     * @return : Trigger ( Job을 실행 시킬 스케줄링 조건 )
     * // *  *  *  *  *  *  *
     * // 초 분  시 일  월 요일 년도(생략가능)
     */
    public Trigger buildCronJobTrigger(String scheduleExp) {
        return TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule(scheduleExp))
                .build();

        //        startAt과 endAt을 사용해 job 스케쥴의 시작, 종료 시간도 지정할 수 있다.
        //        Trigger trigger = TriggerBuilder.newTrigger().startAt(startDateTime).endAt(EndDateTime)
        //                .withSchedule(CronScheduleBuilder.cronSchedule("*/1 * * * *")).build();
    }
}
