package com.souyibao.web.handler;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.souyibao.search.SearchResult;
import com.souyibao.search.ctrl.Controller;
import com.souyibao.web.model.ActionForward;
import com.souyibao.web.model.ActionForwardMapping;
import com.souyibao.web.model.IActionHandler;
import com.souyibao.web.model.SessionData;
import com.souyibao.web.util.MedWebUtil;

public class TopicResultActionHandler implements IActionHandler {

	@Override
	public ActionForward execute(ActionForwardMapping mapping,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String queryString = null;
		
		// query String
		String[] values = request.getParameterValues("q");
		if ((values != null) && (values.length > 0)) {
			for (int i = 0; i < values.length; i++) {
				if (!"".equals(values[i])) {
					queryString = (queryString == null)? values[i] : queryString + " " + values[i];
				}
			}
		}
		
		// for the query keyword ids
		String[] queryKeywordIds = request.getParameterValues("k");

		// get the category filter, topic id -> category id
		String[] cateFilters = request.getParameterValues("cFilter");
		Map<String, String> prefCates = MedWebUtil.supplementPrefCategory(cateFilters);
		
		// get the topic filter
		String[] topicFilters = request.getParameterValues("tFilter");
		
		// query result
		SearchResult searchResult = Controller
				.getSearchResult(queryString, queryKeywordIds, topicFilters, prefCates);
		
		request.setAttribute("searchResult", searchResult);

		// session data;
		SessionData curData = MedWebUtil.makeSessionData(request);
		request.setAttribute("curData", curData);
		
		if ((searchResult.getData() == null) || (searchResult.getData().size() == 1)) {
			return mapping.findForward("topicFilterResult");			
		}
		
		return mapping.findForward("topicResult");
	}
}
