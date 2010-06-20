package com.souyibao.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.souyibao.web.util.MedWebUtil;
import com.souyibao.web.util.WebConstants;

public class AdminServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8") ;
		
		String type = req.getParameter("aType");
		if (!MedWebUtil.isValidAdminType(type)) {
			// TODO: forward to error page.
		}
				
		String operation = req.getParameter("op");
		
		// TODO: needs to validation the query string and operation type
		if (WebConstants.ADD_TYPE.equals(operation)) {
			
		} else if (WebConstants.DELETE_TYPE.equals(operation)) {
			String id = req.getParameter("id");
		} else if (WebConstants.UPDATE_TYPE.equals(operation)) {
			String id = req.getParameter("id");
		}
	}	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	}

}
