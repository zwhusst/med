package com.souyibao.log;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class MedWebLogUtil {
	public static Logger m_UserRequestLogger = Logger.getLogger("UserRequest");

	public static Logger m_ExceptionLogger = Logger.getLogger("Exception");

	public static Logger m_ServerLog = Logger.getLogger("ServerLog");

	public static void logRequest(HttpServletRequest req) {
		StringBuffer msg = new StringBuffer();
		msg.append("[").append(req.getRemoteAddr()).append("] ");
		Enumeration names = req.getParameterNames();
		while (names.hasMoreElements()) {
			String paraKey = (String) names.nextElement();
			String paraValue = req.getParameter(paraKey);

			msg.append("[").append(paraKey).append(": ").append(paraValue)
					.append("] ");
		}

		m_UserRequestLogger.info(msg.toString());
	}

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
