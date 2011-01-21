package com.souyibao.web.handler;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.souyibao.freemarker.DoctorViewer;
import com.souyibao.web.model.ActionForward;
import com.souyibao.web.model.ActionForwardMapping;
import com.souyibao.web.model.IActionHandler;
import com.souyibao.web.util.QueryUtil;

public class TopDoctorActionHandler implements IActionHandler {

	@Override
	public ActionForward execute(ActionForwardMapping mapping,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String cid = request.getParameter("cId");
		if (cid == null) {
			throw new IllegalArgumentException("Category Value has not been specified!");
		}

		List<DoctorViewer> docs = QueryUtil.queryTDocByCategoryID(cid);
		request.setAttribute("tDoclist", docs);
		
		return mapping.successForward();
	}
}
