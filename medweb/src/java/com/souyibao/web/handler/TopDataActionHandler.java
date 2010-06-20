package com.souyibao.web.handler;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.souyibao.shared.MedEntityManager;
import com.souyibao.shared.entity.Area;
import com.souyibao.web.model.ActionForward;
import com.souyibao.web.model.ActionForwardMapping;
import com.souyibao.web.model.IActionHandler;

public class TopDataActionHandler implements IActionHandler {

	@Override
	public ActionForward execute(ActionForwardMapping mapping,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String ks = request.getParameter("ks");
		request.setAttribute("keywords", ks);
		
		Collection<Area> areas = MedEntityManager.getInstance().getAllAreas();
		request.setAttribute("areas", areas);
		
		String kid = request.getParameter("k");
		request.setAttribute("kid", kid);
		
		String cid = request.getParameter("cid");
		request.setAttribute("cid", cid);
		
		return mapping.successForward();		
	}
}
