package com.souyibao.log;

import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.varia.StringMatchFilter;

public class LoggerNameMatchFilter extends StringMatchFilter {

	@Override
	public int decide(LoggingEvent event) {
		String loggerName = event.getLoggerName();

		if (this.getStringToMatch().equalsIgnoreCase(loggerName)) {
			return this.getAcceptOnMatch() ? Filter.ACCEPT : Filter.DENY;
		} else {
			return this.getAcceptOnMatch() ? Filter.DENY : Filter.ACCEPT;
		}
	}
}
