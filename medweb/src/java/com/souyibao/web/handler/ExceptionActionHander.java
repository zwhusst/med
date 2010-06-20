package com.souyibao.web.handler;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.souyibao.log.MedLogUtil;
import com.souyibao.web.model.ActionForward;
import com.souyibao.web.model.ActionForwardMapping;
import com.souyibao.web.model.IActionHandler;

public class ExceptionActionHander implements IActionHandler {

	@Override
	public ActionForward execute(ActionForwardMapping mapping,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setAttribute("hiddenmes", "");
		return mapping.successForward();
	}
	
	public ActionForward execute(ActionForwardMapping mapping,
			HttpServletRequest request, HttpServletResponse response,
			Throwable t) throws Exception {
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream(120);
		PrintStream printStream = new PrintStream(new BufferedOutputStream(
				byteStream));
		t.printStackTrace(printStream);
		printStream.flush();

		String msg = byteStream.toString();
		request.setAttribute("hiddenmes", msg);

		// log the message
		MedLogUtil.logException(t);
		
		return this.execute(mapping, request, response, msg);
	}
	
	
	public ActionForward execute(ActionForwardMapping mapping,
			HttpServletRequest request, HttpServletResponse response, String msg)
			throws Exception {
		request.setAttribute("hiddenmes", msg);

		// log the message
		MedLogUtil.logException(msg);
		
		return mapping.successForward();
	}
}
