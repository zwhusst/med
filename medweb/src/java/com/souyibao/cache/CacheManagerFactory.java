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
package com.souyibao.cache;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class CacheManagerFactory {
	private static Logger logger = Logger.getLogger(CacheManagerFactory.class);
	
	private static Map<String, CacheManager> _cacheManagers = new HashMap<String, CacheManager>();
	
	public static CacheManager getCacheManager(String cacheFolder) {
		CacheManager manager = _cacheManagers.get(cacheFolder);
		
		if (manager == null) {
			try {
				manager = new CacheManager(cacheFolder);
			} catch (IOException e) {
				logger.error("Failed to initialize cache manager for folder " + cacheFolder);
				logger.error(e);
			}
		}
		
		_cacheManagers.put(cacheFolder, manager);
		
		return manager;
	}
	
	public static void destroy(String cacheFolder) {
		CacheManager manager = _cacheManagers.remove(cacheFolder);
		if (manager != null) {
			manager.shutdown();
		}
	}
	
	public static void destoryAllCacheManagers() {
		for (CacheManager manager : _cacheManagers.values()) {
			manager.shutdown();
		}
		
		_cacheManagers.clear();
	}
}
