package com.souyibao.web.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.souyibao.shared.MedEntityManager;
import com.souyibao.shared.entity.Keyword;
import com.souyibao.web.util.MedWebUtil;

public class RefineSearchDlgServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4525297672147962535L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		// test for the input parameter
		System.out.println("current query string : : " + req.getParameter("q"));
		System.out.println("new keyword id : : " + req.getParameter("k"));
		
		String curQueryStr = req.getParameter("q");
		curQueryStr = (curQueryStr == null)? "" : curQueryStr;
				
		String id = req.getParameter("k");
		Keyword keyword = null;
		if (id != null) {
			keyword = MedEntityManager.getInstance().getKeywordById(id);
			//TODO: forward to error page. or do nothing
		}
		
		String keyworkText = "";
		if (keyword != null) {
			keyworkText = MedWebUtil.getKeywordText(keyword);
		}
		
		// put these two paramter to response
		req.setAttribute("curQueryString", curQueryStr);
		req.setAttribute("keywordText", keyworkText);
		
		// encoding the query string for the url
		String link = null;
		try {
			link = URLEncoder.encode(curQueryStr + " " + keyworkText, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// igonre it
		}
		req.setAttribute("allQueryUrl", link);
		
		try {
			link = URLEncoder.encode(keyworkText, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// igonre it
		}
		req.setAttribute("keywordUrl", link);
		
		// forward out
		// the search result will be forwarded to search.jsp		
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/refinesearchdlg.jsp");
		rd.forward(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
}
