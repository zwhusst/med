package com.souyibao.search.module;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class ModuleField {
	private String name = null;
	private String boost = null;

	public ModuleField(String name, String boost){
		this.name = name;
		this.boost = boost;
	}
	
	public ModuleField(Element element) {
		NodeList children = element.getChildNodes();

		String nameVal = null;
		String boostVal = null;

		for (int i = 0; i < children.getLength(); i++) {
			Node childNode = children.item(i);

			if (!(childNode instanceof Element)) {
				continue;
			}

			Element childEle = (Element) childNode;
			if ("name".equals(childEle.getTagName())) {
				nameVal = ((Text) childEle.getFirstChild()).getData();
			} else if ("boost".equals(childEle.getTagName())) {
				boostVal = ((Text) childEle.getFirstChild()).getData();
			}
		}

		if (nameVal != null) {
			this.setName(nameVal);
			this.setBoost((boostVal == null) ? "1" : boostVal);
		} else {
			throw new RuntimeException("name shouldn't be null!");
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBoost() {
		return boost;
	}

	public void setBoost(String boost) {
		this.boost = boost;
	}
}