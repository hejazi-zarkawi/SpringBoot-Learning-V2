package com.example.multiThreading.multiThreading;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class MultiThreadingApplication  {

	public static void main(String[] args) throws InterruptedException {

		SpringApplication.run(MultiThreadingApplication.class, args);

		Thread workerThread= new Thread(()->{
			log.info("Inside the thread "+ Thread.currentThread().getName()+" and state "+ Thread.currentThread().getState());

			try{
				Thread.sleep(5000);
			}catch (InterruptedException e){
				throw new RuntimeException(e);
			}
		});

		workerThread.start();

//		workerThread.join();

		log.info("Inside the thread "+ Thread.currentThread().getName()+" and state "+ Thread.currentThread().getState());
	}



}
