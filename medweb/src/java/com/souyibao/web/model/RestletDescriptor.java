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
package com.souyibao.web.model;

import java.util.ArrayList;
import java.util.List;

import com.souyibao.restlet.BaseRestlet;

public class RestletDescriptor {

    private String name;

    private Boolean enabled = Boolean.TRUE;

    private List<String> urlPatterns = new ArrayList<String>();

    private Class<BaseRestlet> className;
    
    private String template;
    
    private boolean isSingleton = Boolean.FALSE;

    public Class<BaseRestlet> getClassName() {
        return className;
    }

    public void setClassName(Class<BaseRestlet> className) {
        this.className = className;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getUrlPatterns() {
        return urlPatterns;
    }

    public void setUrlPatterns(List<String> urlPatterns) {
        this.urlPatterns = urlPatterns;
    }

    public void addUrlPattern(String data) {
    	this.urlPatterns.add(data);
    }
    
     public boolean isSingleton() {
        return isSingleton;
    }

    public void setIsSingleton(boolean isSingleton) {
        this.isSingleton = isSingleton;
    }

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

}
