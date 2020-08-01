package com.triples.project.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class JobAdvice {

    @Around("execution(* com.triples.project.scheduler.crawling..*.execute*(..))")
    public Object logTime( ProceedingJoinPoint pjp) throws Throwable {

        long start = System.currentTimeMillis();

        log.info("Target: " + pjp.getSignature().getDeclaringTypeName());
        log.info("Param: " + Arrays.toString(pjp.getArgs()));
        Object[] param = pjp.getArgs();

        Object result = null;

        try {
            result = pjp.proceed();
        }catch (Exception e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();

        log.info("TIME: " + (end - start) / 1000.0);

        return result;
    }
}
