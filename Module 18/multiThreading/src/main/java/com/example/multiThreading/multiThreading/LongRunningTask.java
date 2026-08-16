package com.example.multiThreading.multiThreading;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LongRunningTask implements Runnable{

    private int currentThread;
    public LongRunningTask(int i) {
        this.currentThread=i;
    }

    @Override
    public void run() {
        log.info("Starting task in thread {} {}",currentThread,Thread.currentThread().getName());

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log.info("Ending task in thread {}",Thread.currentThread().getName());
    }
}
