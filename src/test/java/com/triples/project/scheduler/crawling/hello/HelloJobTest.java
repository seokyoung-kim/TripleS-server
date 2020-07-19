package com.triples.project.scheduler.crawling.hello;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


public class HelloJobTest {

    @Test
    public void helloJob() throws SchedulerException, InterruptedException {

        JobDataMap jobDataMap1 = new JobDataMap();
        jobDataMap1.put("JobName","Job Chain 1");

        JobDetail jobDetail1 = JobBuilder.newJob(HelloJob.class)
        .usingJobData(jobDataMap1).build();

        Trigger trigger1 = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder
        .cronSchedule("2 * * * * ?")).build();


        JobDataMap jobDataMap2 = new JobDataMap();
        jobDataMap2.put("JobName","Job Chain 2");

        JobDetail jobDetail2 = JobBuilder.newJob(HelloJob.class)
                .usingJobData(jobDataMap2).build();

        Trigger trigger2 = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder
                .cronSchedule("2 * * * * ?")).build();


        JobDataMap jobDataMap3 = new JobDataMap();
        jobDataMap3.put("JobName","Job Chain 3");

        JobDetail jobDetail3 = JobBuilder.newJob(HelloJob.class)
                .usingJobData(jobDataMap3).build();

        Trigger trigger3 = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder
                .cronSchedule("2 * * * * ?")).build();




        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();


        scheduler.start();
        scheduler.scheduleJob(jobDetail1, trigger1);
       // scheduler.scheduleJob(jobDetail2, trigger2);
        //scheduler.scheduleJob(jobDetail3, trigger3);
        Thread.sleep(3 * 1000 );

        System.out.println(">>>>>>>>>>."+scheduler.getSchedulerInstanceId());

        scheduler.shutdown();
    }
}