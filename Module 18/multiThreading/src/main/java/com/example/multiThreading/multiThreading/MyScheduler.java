package com.example.multiThreading.multiThreading;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyScheduler {
        @Scheduled(fixedDelay = 2000)
        @Async("jobExecutor")
        void logMe(){
            log.info("Scheduled task started {}",Thread.currentThread().getName());
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("Scheduled task ended {}",Thread.currentThread().getName());
        }


//    @Scheduled(fixedRate = 1000)
//    void logYou(){
//        log.info("Scheduled 2 task started {}",Thread.currentThread().getName());
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        log.info("Scheduled 2 task ended {}",Thread.currentThread().getName());
//    }
}
