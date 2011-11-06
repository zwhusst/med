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
package com.souyibao.freemarker;

import java.io.IOException;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class ConfigurationContext {
	private static ConfigurationContext cfgContext = new ConfigurationContext();
	private Configuration templateCfg = null;
	
	private ConfigurationContext() {
		templateCfg = new Configuration();
	}
	
	public static ConfigurationContext getInstance() {
		return cfgContext;
	}
	
	public void setTemplateCfgSettings(String name, String value) {
		try {
			this.templateCfg.setSetting(name, value);
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}
	
	public void setServletContextForTemplateLoading(Object object, String path) {
		this.templateCfg.setServletContextForTemplateLoading(object, path);
	}
	
	public Template getTemplate(String name) throws IOException {
		return this.templateCfg.getTemplate(name);
	}
}
