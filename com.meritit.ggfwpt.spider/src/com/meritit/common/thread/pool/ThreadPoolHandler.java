package com.meritit.common.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolHandler {

	private static final ExecutorService POOL = Executors.newFixedThreadPool(5);
	
	public static ThreadPoolHandler getInstance(){
		return ThreadPoolHolder.pollHander;
	}
	
	public void execute(Runnable r){
		POOL.execute(r);
	}
	
	public void shutdown(){
		POOL.shutdown();
	}
	
	private static class ThreadPoolHolder{
		private static ThreadPoolHandler pollHander = new ThreadPoolHandler();
	}
}
