package com.triples.project.scheduler.crawling;

import com.triples.project.scheduler.crawling.brunch.BrunchJob;
import com.triples.project.scheduler.crawling.festa.FestaJob;
import com.triples.project.scheduler.crawling.velog.VelogJob;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


/**
 *
 * @description : Job 을 정해진 시간에 실행 하도록 설정 ( Crawling Scheduler )
 */
@Component
@RequiredArgsConstructor
public class CrawlingScheduler {

    private final Scheduler scheduler;

    @PostConstruct
    public void start() throws SchedulerException, InterruptedException {

//        //job 지정
//        JobDetail job = JobBuilder.newJob(FestaJob.class).withIdentity("festa").build();
        
        JobDataMap jobDataMap1 = new JobDataMap();
        jobDataMap1.put("JobName","Job Chain 1"); // JobName 을 Job Chain 1 로 지정
        JobDetail jobDetail = JobBuilder.newJob(VelogJob.class).usingJobData(jobDataMap1).build();

        JobDataMap jobDataMap2 = new JobDataMap();
        jobDataMap2.put("JobName","Job Chain 2");
        JobDetail jobDetail2 = JobBuilder.newJob(FestaJob.class).usingJobData(jobDataMap2).build();

        JobDataMap jobDataMap3 = new JobDataMap();
        jobDataMap3.put("JobName","Job Chain 3");
        JobDetail jobDetail3 = JobBuilder.newJob(BrunchJob.class).usingJobData(jobDataMap3).build();


        // 매일 오후 18시마다 실행
        scheduler.scheduleJob(jobDetail, buildCronJobTrigger("0 0 18 * * ?"));
        scheduler.scheduleJob(jobDetail2, buildCronJobTrigger("0 0 18 * * ?"));
        scheduler.scheduleJob(jobDetail3, buildCronJobTrigger("0 0 18 * * ?"));

        Thread.sleep(3 * 1000);  // Job이 실행될 수 있는 시간 여유를 준다

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
