package com.Logger;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

public class Logger {
	static ConcurrentHashMap<String, Logger> instanceMap = new ConcurrentHashMap<String, Logger>();
	IAppender appender;
	Executor executor;
	PriorityBlockingQueue<LogJob> priorityQueue;
	Executor singleThreadExecutor;
	final int N_THREADS = 10;
	final int QUEUE_SIZE = 100000;
	
	private class LogJob implements Runnable {
		String _message;
		LogLevel _level;
		Timestamp _timestamp;

		public LogJob(String message, LogLevel level, Timestamp timestamp) {
			this._message = message;
			this._level = level;
			this._timestamp = timestamp;
		}

		public String get_message() {
			return _message;
		}

		public LogLevel get_level() {
			return _level;
		}

		public Timestamp get_timestamp() {
			return _timestamp;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			appender.Append(_timestamp, _level, _message);
		}

	}

	private Logger(IAppender appender) {
		this.appender = appender;
		this.singleThreadExecutor = Executors.newSingleThreadExecutor();
		this.executor = Executors.newFixedThreadPool(N_THREADS);
		this.priorityQueue = new PriorityBlockingQueue<LogJob>(QUEUE_SIZE, Comparator.comparing(LogJob::get_timestamp));
		this.singleThreadExecutor.execute(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					try {
						executor.execute(priorityQueue.take());
					} catch (InterruptedException e) {
						System.out.println("InterruptedException occurred");
					}
				}
			}

		});
	}

	public void Log(LogLevel level, String message) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		this.priorityQueue.add(new LogJob(message, level, timestamp));
	}
	
	
	public static Logger CreateLogger(IAppender appender) {		
		instanceMap.putIfAbsent(appender.toString(), new Logger(appender));	
		return instanceMap.get(appender.toString());
	}
}
