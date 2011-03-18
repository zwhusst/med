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
