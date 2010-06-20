package com.souyibao.web.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.souyibao.shared.MedEntityManager;
import com.souyibao.shared.entity.Keyword;
import com.souyibao.web.model.ActionForward;
import com.souyibao.web.model.ActionForwardMapping;
import com.souyibao.web.model.IActionHandler;
import com.souyibao.web.model.KeywordExplainModel;

public class RefineSearchDlgActionHandler implements IActionHandler {

	@Override
	public ActionForward execute(ActionForwardMapping mapping,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String epId = request.getParameter("epId");
		String userInput = request.getParameter("q");
		String[] checkedIds = request.getParameterValues("k"); 
		String[] tFilters = request.getParameterValues("tFilter");
		String[] cFilters = request.getParameterValues("cFilter");
		
		Keyword keyword = null;
		if (epId != null) {
			 keyword = MedEntityManager.getInstance().getKeywordById(epId);
		}
		
		KeywordExplainModel explanation = new KeywordExplainModel();
		explanation.setUserInputTxt(userInput);
		explanation.setKeyword(keyword);
		explanation.setCheckedIds(checkedIds);
		explanation.setTFilters(tFilters);
		explanation.setCFilters(cFilters);
		
		request.setAttribute("ep", explanation);
		
		return mapping.successForward();
	}
}
