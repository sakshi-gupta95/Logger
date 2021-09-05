package com.Logger;

import java.sql.Timestamp;

public class ConsoleAppender implements IAppender {

	@Override
	public void Append(Timestamp timestamp, LogLevel level, String message) {
		System.out.println("Timestamp: " + timestamp 
				+ " Level: " + level 
				+ " Message: " + message
				+ " Thread ID: " + Thread.currentThread().getId());
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "ConsoleAppender";
	}

	

	
}
