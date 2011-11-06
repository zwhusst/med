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
package com.souyibao.restlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.MediaType;

import com.souyibao.freemarker.ConfigurationContext;
import com.souyibao.freemarker.SearchDataModel;
import com.souyibao.web.model.RepresentationMeta;
import com.souyibao.web.model.SimpleStringRepresentation;

import freemarker.template.Template;

public class EmptySearchRestlet extends BaseRestlet {
	private static Logger logger = Logger.getLogger(EmptySearchRestlet.class);
	
	@Override
	public Template getTemplate() {
		String templateName = "search.ftl";
		try {
			return ConfigurationContext.getInstance().getTemplate(templateName);
		} catch (IOException e) {
			logger.error("Failed to find template " + templateName, e);
		}
		
		return null;
	}

	@Override
	public SimpleStringRepresentation processRequest(Request request,
			Response response) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("searchData", SearchDataModel.DUMMY_DATA_MODEL);
		String outValue = this.processData(request, data);

		RepresentationMeta meta = new RepresentationMeta(
				MediaType.TEXT_HTML.getName(), "UTF-8");
		// output
		return new SimpleStringRepresentation(meta, outValue);

	}

}
