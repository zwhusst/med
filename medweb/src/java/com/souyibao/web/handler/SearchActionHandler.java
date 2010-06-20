package com.souyibao.web.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.souyibao.web.model.ActionForward;
import com.souyibao.web.model.ActionForwardMapping;
import com.souyibao.web.model.ExSessionData;
import com.souyibao.web.model.IActionHandler;
import com.souyibao.web.util.MedWebUtil;

public class SearchActionHandler implements IActionHandler {

	@Override
	public ActionForward execute(ActionForwardMapping mapping,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// check the search parameter
		String queryString = request.getParameter("q");
		if (queryString == null) {
			queryString = "";
		}
		
		ExSessionData exdata = MedWebUtil.makeExSessionData(request);
		request.setAttribute("exdata", exdata);
		
		request.setAttribute("sp", queryString);

		return mapping.successForward();
	}
}
