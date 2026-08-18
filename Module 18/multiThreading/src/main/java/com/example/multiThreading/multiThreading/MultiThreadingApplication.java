package com.example.multiThreading.multiThreading;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.Duration;
import java.util.concurrent.*;


@SpringBootApplication
@Slf4j
@EnableScheduling
@EnableAsync
public class MultiThreadingApplication {

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		SpringApplication.run(MultiThreadingApplication.class, args);

//		learnCF2();

		log.info("Back to main thread");

	}

	static void learnThread(){
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

	static void learnFuture() throws ExecutionException, InterruptedException {

		try  {

			ExecutorService executorService = Executors.newFixedThreadPool(4);

			Future<String> myNameFuture = executorService.submit(() -> getName());

			myNameFuture.get(); // block the calling thread
			log.info("After nameFuture: {}", Thread.currentThread().getState());
		}
		catch(Exception e){
			throw new ExecutionException(e);
		}
	}


	static void learnCompletableFuture() {
		CompletableFuture<String> myNameCF = CompletableFuture
				.supplyAsync(() -> getName())
				.thenApply(name -> name.toUpperCase())
				.thenApply(upperCaseName -> upperCaseName.length())
				.thenApplyAsync(lengthOfName -> {
					log.info("Inside method with length");
					if(true) throw new RuntimeException("Faking an error.");
					return "length was "+lengthOfName;
				})
				.exceptionally((err) -> {
					return "Default value in case of failure";
				});

		myNameCF.thenAccept(name -> {
			log.info("Got the name length: {}", name);
		});

		log.info("After completing Thread");
	}

	static void learnCF2() {
		CompletableFuture<String> nameFuture = CompletableFuture.supplyAsync(() -> getName());
		CompletableFuture<String> addressFuture = CompletableFuture.supplyAsync(() -> getAddress());

		CompletableFuture.allOf(nameFuture, addressFuture)
				.thenAccept((v) -> {
					log.info("Got the name: {} and address here: {}", nameFuture.join(), addressFuture.join());
				});

//        log.info("Got the name: {} and address here: {}", nameFuture.join(), addressFuture.join());
	}
	static String getName() {
		try {
			log.info("Inside nameFuture: {}", Thread.currentThread().getState());
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		return "Anuj";
	}

	static String getAddress() {
		try {
			log.info("Inside Addressfuture: {}", Thread.currentThread().getState());
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		return "New Delhi";
	}

}
