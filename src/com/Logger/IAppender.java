package com.Logger;

import java.sql.Timestamp;

public interface IAppender {
	public void Append(Timestamp timestamp, LogLevel level, String message);
}
