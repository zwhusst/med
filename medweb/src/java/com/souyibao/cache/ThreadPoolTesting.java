package com.souyibao.cache;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTesting {

	private ThreadPoolExecutor executor = null;
    private LinkedBlockingQueue queue = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
//		ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 4, 3,
//				TimeUnit.SECONDS, new LinkedBlockingQueue(),
//				new ThreadPoolExecutor.AbortPolicy());
		new ThreadPoolTesting().run();
	}

	private void run() throws Exception {
//		executor = Executors.newFixedThreadPool(1);
		queue = new LinkedBlockingQueue();
		executor = new ThreadPoolExecutor(1, 1, 1000, TimeUnit.SECONDS, queue,
				new ThreadPoolExecutor.AbortPolicy());
		
		for (int i = 0; i < 20;  i++) {
			String taskId = "task: " + i;
			executor.execute(new ThreadTask(taskId));
			
			if (i == 10) {
				Thread.currentThread().sleep(3000);
				executor.execute(new ThreadTask("----temporary task---"));
			}
		}
		
		executor.shutdown();
	}
	
	private long getTaskCount() {
		return executor.getQueue().size() - queue.size();
	}
	
	class ThreadTask implements Runnable {
		private String taskId = null;
		
		public ThreadTask(String taskId) {
			this.taskId = taskId;
		}
		
		@Override
		public void run() {
			System.out.println("my task is: " + taskId);
			try {
				Thread.sleep(2000);
				System.out.println("remaining task count: " + 
				ThreadPoolTesting.this.getTaskCount());
				
				System.out.println("------------------------------------");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
