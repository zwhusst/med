package com.souyibao.web.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.souyibao.web.model.ActionForward;
import com.souyibao.web.model.ActionForwardMapping;
import com.souyibao.web.model.IActionHandler;

public class EntityActionHander implements IActionHandler {

	@Override
	public ActionForward execute(ActionForwardMapping mapping,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String type = request.getParameter("t");
		
		if ("h".equals(type)) {
			
		}
		
		return mapping.findForward(type);
	}

}
