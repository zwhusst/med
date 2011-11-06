/** 
* Copyright (c) 2011-2013  上海宜豪健康信息咨询有限公司 版权所有 
* Shanghai eHealth Technology Company. All rights reserved. 

* This software is the confidential and proprietary 
* information of Shanghai eHealth Technology Company. 
* ("Confidential Information"). You shall not disclose 
* such Confidential Information and shall use it only 
* in accordance with the terms of the contract agreement 
* you entered into with Shanghai eHealth Technology Company. 
*/
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
