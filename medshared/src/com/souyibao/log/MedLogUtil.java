package com.souyibao.log;

import java.util.Enumeration;

import org.apache.log4j.Logger;

public class MedLogUtil {
	public static Logger m_UserRequestLogger = Logger.getLogger("UserRequest");

	public static Logger m_ExceptionLogger = Logger.getLogger("Exception");

	public static Logger m_ServerLog = Logger.getLogger("ServerLog");

	public static void logException(Throwable t) {
		m_ExceptionLogger.error(t);
	}

	public static void logException(String msg) {
		m_ExceptionLogger.error(msg);
	}

	public static void logInfo(String msg) {
		m_ServerLog.info(msg);
	}
}
