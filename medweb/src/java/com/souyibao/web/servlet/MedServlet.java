package com.souyibao.web.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.souyibao.log.MedWebLogUtil;
//import com.souyibao.web.ActionHandlerManager;
import com.souyibao.web.model.ActionForward;
import com.souyibao.web.model.ActionHandlerModel;

public class MedServlet extends HttpServlet {
    private static Logger logger = Logger.getLogger(MedServlet.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -1632733055728957364L;

	@Override
	public void init() throws ServletException {
		super.init();
		
		// for the document idx folder
		String idxPath = this.getServletContext().getRealPath("idx");
		System.getProperties().setProperty("document_module_idx", idxPath);
		
		String doctorPath = this.getServletContext().getRealPath("doctoridx");
		System.getProperties().setProperty("doctor_module_idx", doctorPath);
		
		// configuration for log4j
		// set the file path for the log
		String logPath = this.getServletContext().getRealPath("logs");
		System.getProperties().setProperty("log4j_path", logPath);
		String prefix =  getServletContext().getRealPath("/");
	    String file = getInitParameter("log4j-init-file");
	    // if the log4j-init-file is not set, then no point in trying
	    if(file != null) {
	    	DOMConfigurator.configure(prefix+file);
	    }
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
//		req.setCharacterEncoding("UTF-8");		
//		MedWebLogUtil.logRequest(req);
//		
//		// handler
//		ActionForward forward = null;
//		String handlerName = req.getParameter("hl");
//		if (handlerName == null) {
//			forward = ActionHandlerManager.getInstance().executeExceptionHandler(req,
//					resp, new RuntimeException("Handler name is empty"));
//			RequestDispatcher rd = getServletContext().getRequestDispatcher(
//					forward.getPath());
//			rd.forward(req, resp);
//			
//			return;
//		}
//
//		ActionHandlerModel handlerModel = ActionHandlerManager.getInstance()
//				.getActionHandler(handlerName);
//		if (handlerModel == null) {
//			forward = ActionHandlerManager.getInstance().executeExceptionHandler(req,
//					resp, new RuntimeException("Can't find the handler with name: " + handlerName));
//			RequestDispatcher rd = getServletContext().getRequestDispatcher(
//					forward.getPath());
//			rd.forward(req, resp);
//			
//			return;
//		}
//
//		try {
//			String forwardName = req.getParameter("fw");
//			forward = ActionHandlerManager.getInstance()
//					.executeHandler(req, resp, handlerModel, forwardName);
//			if (forward == null) {
//				// the possible case is the handler to write data to response
//				// directly.
//				return;
//			}
//
//			while (needForwardNext(forward)) {
//				forward = ActionHandlerManager.getInstance().executeHandler(
//						req, resp, handlerModel, forwardName);
//				if (forward == null) {
//					return;
//				}
//			}
//
//			// the search result will be forwarded to search.jsp
//			RequestDispatcher rd = getServletContext().getRequestDispatcher(
//					forward.getPath());
//			rd.forward(req, resp);
//		} catch (Exception e) {
//			ActionHandlerManager.getInstance().executeExceptionHandler(req,
//					resp, e);
//		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	private boolean needForwardNext(ActionForward forward) {
//		String path = forward.getPath();
//		ActionHandlerModel hlModel = ActionHandlerManager.getInstance()
//				.getActionHandler(path);
//
//		return (hlModel == null) ? false : true;
		return false;
	}
}
