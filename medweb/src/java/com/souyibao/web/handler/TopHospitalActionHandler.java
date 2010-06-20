package com.souyibao.web.handler;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.souyibao.web.model.ActionForward;
import com.souyibao.web.model.ActionForwardMapping;
import com.souyibao.web.model.IActionHandler;
import com.souyibao.web.util.MedWebUtil;
import com.souyibao.web.viewer.HospitalViewer;

public class TopHospitalActionHandler implements IActionHandler {

	@Override
	public ActionForward execute(ActionForwardMapping mapping,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// check get cid and aid
		String categoryId = request.getParameter("cId");
		String areaId = request.getParameter("aId");
		
		if (categoryId == null) {			
			return mapping.successForward();
		}

		// forward to hospital list page
		areaId = (areaId == null)? "0" : areaId;
		List<HospitalViewer> hospitals = MedWebUtil.getFilterHospital(categoryId, areaId);
		request.setAttribute("thosList", hospitals);
		return mapping.successForward();

	}
}
