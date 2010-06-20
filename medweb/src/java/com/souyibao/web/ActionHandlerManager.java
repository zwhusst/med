package com.souyibao.web;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.souyibao.web.model.ActionForward;
import com.souyibao.web.model.ActionForwardMapping;
import com.souyibao.web.model.ActionHandlerModel;
import com.souyibao.web.model.IActionHandler;
import com.souyibao.web.handler.ExceptionActionHander;

public class ActionHandlerManager {
	private static ActionHandlerManager instance = new ActionHandlerManager();
	
	// some constants for the handler name
	public static final String EXCEPTION_HANDLER = "exception";	
	
	// handler name to action handler
	private Map<String, ActionHandlerModel> nameToActionHandlers = new HashMap<String, ActionHandlerModel>();

	// handler name to handler instance
	private Map<String, IActionHandler> nameToHanlderInstance = new HashMap<String, IActionHandler>();
	
	private ActionHandlerManager() {
		loadData();
		initializeHandlerInstance();
	}

	public static ActionHandlerManager getInstance() {
		return instance;
	}
	
	private void loadData() {
		URL resourceURL = ActionHandlerManager.class.getClassLoader().getResource(
				"ActionHandlers.xml");
		
		Document doc = null;

		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			doc = builder.parse(resourceURL.toString());
		} catch (Exception e) {
			throw new RuntimeException(
					"Exception occured in loading action handler file : ActionHandlers.xml");
		}

		if (doc == null) {
			throw new RuntimeException(
					"Exception occured in parse action handler file : ActionHandlers.xml");
		}

		Element root = doc.getDocumentElement();
		NodeList handlerNodeList = root.getChildNodes();
		for (int i = 0; i < handlerNodeList.getLength(); i++) {
			Node handlerNode = handlerNodeList.item(i);
			if (!(handlerNode instanceof Element))
				continue;

			Element handlerEle = (Element) handlerNode;
			if ("handler".equals(handlerEle.getTagName())) {
				ActionHandlerModel handlerModel = new ActionHandlerModel(handlerEle);
				nameToActionHandlers.put(handlerModel.getName(), handlerModel);
			}
		}
	}
	
	public ActionForward executeExceptionHandler(HttpServletRequest req,
			HttpServletResponse resp, Throwable t) {
		ExceptionActionHander handler = getExceptionHandler();
		
		ActionHandlerModel handlerModel = nameToActionHandlers.get(EXCEPTION_HANDLER);
		try {
			return handler.execute(new ActionForwardMapping(handlerModel), req, resp, t);
		} catch (Exception e) {
			// log it;
		}
		
		return null;
	}

	public ActionForward executeExceptionHandler(HttpServletRequest req,
			HttpServletResponse resp, String msg) {
		ExceptionActionHander handler = getExceptionHandler();
		
		ActionHandlerModel handlerModel = nameToActionHandlers.get(EXCEPTION_HANDLER);
		try {
			return handler.execute(new ActionForwardMapping(handlerModel), req, resp, msg);
		} catch (Exception e) {
			// log it;
		}

		return null;
	}
	
	/**
	 * Get the exception handler, only one exception handler can be defined
	 * @return
	 */
	public ExceptionActionHander getExceptionHandler() {
		IActionHandler handlerInstance = nameToHanlderInstance
				.get(EXCEPTION_HANDLER);
		if (handlerInstance == null) {
			throw new RuntimeException(
					"No exception handler has been specified with name: "
							+ EXCEPTION_HANDLER);
		}

		return (ExceptionActionHander) handlerInstance;
	}
	
	public ActionHandlerModel getActionHandler(String name) {
		return nameToActionHandlers.get(name);
	}

	private Collection<ActionHandlerModel> getActionHandlers() {
		return nameToActionHandlers.values();
	}
	
	private void initializeHandlerInstance() {
		Set<String> handlerNames = nameToActionHandlers.keySet();

		IActionHandler handlerInstance = null;
		for (Iterator<String> iterator = handlerNames.iterator(); iterator
				.hasNext();) {
			String name = iterator.next();
			ActionHandlerModel handlerModel = nameToActionHandlers.get(name);

			String type = handlerModel.getType();
			try {
				Class handerClass = Class.forName(type);
				handlerInstance = (IActionHandler) handerClass.newInstance();

				nameToHanlderInstance.put(name, handlerInstance);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	public ActionForward executeHandler(HttpServletRequest req,
			HttpServletResponse resp, ActionHandlerModel handler, String forward)
			throws Exception {
		String name = handler.getName();
		IActionHandler handlerInstance = nameToHanlderInstance.get(name);
		
		if (handlerInstance == null) {
			throw new RuntimeException("Failed to create instance for: "
					+ handler.getName());
		}

		ActionForward newForward = handlerInstance.execute(new ActionForwardMapping(
				handler), req, resp);
		if (forward != null) {
			// the forward has been specified
			return handler.getForwardPath(forward);
		}
		
		return newForward;
	}
	
	public static void main(String[] args) throws Exception {
		ActionHandlerManager aa = ActionHandlerManager.getInstance();
		Collection<ActionHandlerModel> bb = aa.getActionHandlers();
		
		for (Iterator<ActionHandlerModel> iterator = bb.iterator(); iterator.hasNext();) {
			ActionHandlerModel handler = iterator.next();
			
			System.out.println("handler name: " + handler.getName());
			System.out.println("handler type: " + handler.getType());
			
			Map<String, ActionForward> forwards = handler.getForwards();
			Set keys = forwards.keySet();
			for (Iterator<ActionForward> forwardIte = keys.iterator(); forwardIte.hasNext();) {
				ActionForward actionForward = forwardIte.next();
				System.out.println("forward name: " + actionForward.getName());
				System.out.println("forward path: " + actionForward.getPath());
			}
			
			System.out.println("-------------------------");
		}		
	}
}
