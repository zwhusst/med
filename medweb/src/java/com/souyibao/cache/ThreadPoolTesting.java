package com.souyibao.cache;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTesting {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 4, 3,
//				TimeUnit.SECONDS, new LinkedBlockingQueue(),
//				new ThreadPoolExecutor.AbortPolicy());
		ExecutorService executor = Executors.newFixedThreadPool(1);
		
		for (int i = 0; i < 20;  i++) {
			String taskId = "task: " + i;
			executor.execute(new ThreadTask(taskId));
		}
		
		executor.shutdown();
	}

	private static class ThreadTask implements Runnable {
		private String taskId = null;
		
		public ThreadTask(String taskId) {
			this.taskId = taskId;
		}
		
		@Override
		public void run() {
			System.out.println("my task is: " + taskId);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
