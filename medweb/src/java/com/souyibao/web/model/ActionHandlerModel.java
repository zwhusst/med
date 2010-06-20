package com.souyibao.web.model;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ActionHandlerModel {
	private String name = null;
	private String type = null;
	private Map<String, ActionForward> forwards = new HashMap<String, ActionForward>();

	public ActionHandlerModel(Element element) {
		this.name = element.getAttribute("name");
		this.type = element.getAttribute("type");

		NodeList childList = element.getChildNodes();
		for (int i = 0; i < childList.getLength(); i++) {
			Node forwardNode = childList.item(i);
			if (!(forwardNode instanceof Element))
				continue;

			Element forwardEle = (Element) forwardNode;
			if ("forward".equals(forwardEle.getTagName())) {
				String forwardName = forwardEle.getAttribute("name");
				String path = forwardEle.getAttribute("path");
				
				ActionForward actionForward = new ActionForward(forwardName, path);
				
				forwards.put(forwardName, actionForward);
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Map<String, ActionForward> getForwards() {
		return forwards;
	}

	public ActionForward getForwardPath(String name) {
		return forwards.get(name);
	}
}
