package com.example.multiThreading.multiThreading;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Duration;
import java.util.concurrent.*;


@SpringBootApplication
@Slf4j
public class MultiThreadingApplication implements CommandLineRunner {

	public static void main(String[] args)  {

		SpringApplication.run(MultiThreadingApplication.class, args);

	}


	@Override
	public void run(String... args) throws Exception {
		ThreadPoolExecutor threadPoolExecutor= new ThreadPoolExecutor(4,6,
				2,java.util.concurrent.TimeUnit.SECONDS,
				new ArrayBlockingQueue<>(10),
				new RejectedExecutionHandler() {
					@Override
					public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
						log.info("Thread rejected.... Retrying....");

						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							throw new RuntimeException(e);
						}

						executor.submit(r);
					}
				})
		;

		ScheduledThreadPoolExecutor scheduledThreadPoolExecutor= new ScheduledThreadPoolExecutor(6,
				new ThreadFactory() {
					@Override
					public Thread newThread(Runnable r) {
						return new Thread(r, "thread");
					}
				});

		scheduledThreadPoolExecutor.schedule(new LongRunningTask(1),4, TimeUnit.SECONDS);

		log.info("Running thread {}",Thread.currentThread().getName());

//		for(int i=0; i<20;i++) {
//			threadPoolExecutor.submit(new LongRunningTask(i));
//
//		}
		log.info("Ending thread {}",Thread.currentThread().getName());
	}
}
