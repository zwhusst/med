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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.MediaType;

import com.souyibao.freemarker.KeywordDetailsDataModel;
import com.souyibao.shared.MedEntityManager;
import com.souyibao.shared.entity.Keyword;
import com.souyibao.web.model.RepresentationMeta;
import com.souyibao.web.model.SimpleStringRepresentation;

public class KeywordDetailsRestlet extends BaseRestlet {
	
	@Override
	public SimpleStringRepresentation processRequest(Request request, Response response) {
		String keywordid = (String)request.getAttributes().get("keywordid");
		String querykeyword = (String)request.getAttributes().get("querykeyword");
//		String querystring = (String)request.getAttributes().get("querystring");
		String querystring = request.getResourceRef().getQueryAsForm().getFirstValue("qs", null);
		String categoryid = (String)request.getAttributes().get("categoryid");
		String topicid = (String)request.getAttributes().get("topicid");
		String outersite = request.getResourceRef().getQueryAsForm().getFirstValue("outersite", null);
		
		if (keywordid == null) {
			return null;
		}
		
		String[] querykeywordids = null;
		if (querykeyword != null) {
			querykeywordids = querykeyword.split(",");
		}
		
		Keyword keyword = MedEntityManager.getInstance().getKeywordById(keywordid);
		if (keyword == null) {
			return null;
		}
		
		if ((querystring != null) && (querystring.length() > 0)) {
			try {
				querystring = URLDecoder.decode(querystring, "UTF-8");
			} catch (UnsupportedEncodingException e) {
			}
		}
		
		KeywordDetailsDataModel dataModel = handleInput(keyword,
				querykeywordids, querystring, categoryid, topicid, outersite);
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("detailsData", dataModel);
		String outValue = this.processData(request, data);

		RepresentationMeta meta = new RepresentationMeta(MediaType.TEXT_HTML.getName(), "UTF-8");
		// output
		return new SimpleStringRepresentation(meta, outValue);
	}
	
	private KeywordDetailsDataModel handleInput(Keyword keyword,
			String[] querykeywordids, String querystring, String categoryid,
			String topicid, String outersite) {
		// keyword for the explanation
		KeywordDetailsDataModel dataModel = new KeywordDetailsDataModel();
		dataModel.setKeyword(keyword);
		dataModel.setOuterSite(outersite);
		
		// query keywords;
		Collection<Keyword> queryKeywords = new ArrayList<Keyword>();
		if (querykeywordids != null) {
			for (int i = 0; i < querykeywordids.length; i++) {
				Keyword queryKeyword = MedEntityManager.getInstance().getKeywordById(querykeywordids[i]);
				
				if (queryKeyword != null) {
					queryKeywords.add(queryKeyword);
				}
			}
		}
		dataModel.setQuerykeywords(queryKeywords);
		
		dataModel.setUserQuery(querystring);
		dataModel.setCategoryid(categoryid);
		dataModel.setTopicid(topicid);
		
		// keyword explanation
		String explanation = MedEntityManager.getInstance().getKeywordExplanation(keyword);
		dataModel.setExplanation(explanation);
		
		return dataModel;
	}
}
