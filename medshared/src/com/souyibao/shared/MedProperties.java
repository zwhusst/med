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
package com.souyibao.shared;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public class MedProperties {
	private static Logger logger = Logger.getLogger(MedProperties.class);
	
	private Properties properties = null;

	private final String MAX_RECORDS_FOR_ALL_SEARCH = "max.records.for.all.search";
	private final String MAX_RECORD_FOR_SINGLE_SEARCH = "max.record.for.single.search";

	private final String GUIDE_INFO_TOPIC_IDS = "guide.info.topic.ids";
	private final String SHOWED_SEARCH_TOPIC_IDS = "showed.search.topic.ids";
	
	// default value
	public static int DEF_MAX_RECORD_NUM_4_ALL = 6;
	public static int DEF_MAX_RECRD_NUM_PER_TOPIC = 50;

	private static MedProperties _instance = null;
	
	private MedProperties(Properties properties) {
		this.properties = properties;
	}
	
	private MedProperties(InputStream is) throws IOException {
		this.properties = new Properties();
		this.properties.load(is);
	}

	public Object getProperty(String name, Object defValue) {
		Object value = this.properties.get(name);

		return (value == null) ? defValue : value;
	}

	public int getRecordNum4All(int defValue) {
		Object value = this.getProperty(MAX_RECORDS_FOR_ALL_SEARCH, defValue);
		
		return Integer.parseInt(value.toString());
	}

	public int getRecordNumPerTopic(int defValue) {
		Object value = this.getProperty(MAX_RECORD_FOR_SINGLE_SEARCH,
				defValue);
		
		return Integer.parseInt(value.toString());
	}
	
	public List<String> getTopics4Guide() {
		String value = (String)this.getProperty(GUIDE_INFO_TOPIC_IDS, null);
				
		if (value != null) {
			String[] topicIds = value.split(",");
			
			List<String> result = new ArrayList<String>();
			for (int i = 0; i < topicIds.length; i++) {
				result.add(topicIds[i].trim());
			}
			
			return result;
		}
		
		return null;
	}
	
	public List<String> getTopics4Showed() {
		String value = (String)this.getProperty(SHOWED_SEARCH_TOPIC_IDS, null);
				
		if (value != null) {
			String[] topicIds = value.split(",");
			
			List<String> result = new ArrayList<String>();
			for (int i = 0; i < topicIds.length; i++) {
				result.add(topicIds[i].trim());
			}
			
			return result;
		}
		
		return null;
	}
	
	public static MedProperties getInstance() {
		InputStream is = MedProperties.class.getClassLoader().getResourceAsStream("med.properties");
		
		try {
			_instance = new MedProperties(is);
		} catch (IOException e) {
			logger.error(e);
		}
		
		return _instance;
	}
	
	public int getValueAsInt(String key) {
		Object obj = this.properties.get(key);
		if (obj == null) {
			return 0;
		}
		
		return Integer.parseInt(obj.toString());
	}
	
	
	public static void main(String[] args) throws Exception{
		Properties props = new Properties();
		props.load(new FileInputStream(new File("bin/med.properties")));
		
		MedProperties medProps = new MedProperties(props);
		
		System.out.println(medProps.getRecordNum4All(23));
		System.out.println(medProps.getRecordNumPerTopic(1));
		
		
		System.out.println(medProps.getTopics4Guide());
	}
}
