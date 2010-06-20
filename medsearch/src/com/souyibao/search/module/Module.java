package com.souyibao.search.module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class Module {
	private static final Log LOG = LogFactory.getLog("com.med.module.Module");

	private Collection<String> indexs = new ArrayList<String>();
	private Collection<ModuleField> fields = new ArrayList<ModuleField>();
	private List<String> topicIds = null;
	private String name = null;
	
	public Module(String name, Element element) {
		this.name = name;
		
		NodeList moduleChildren = element.getChildNodes();

		for (int i = 0; i < moduleChildren.getLength(); i++) {
			Node moduleChild = moduleChildren.item(i);
			if (!(moduleChild instanceof Element)) {
				continue;
			}

			Element moduleChildEle = (Element) moduleChild;
			if (("indexs").equals(moduleChildEle.getTagName())) {
				loadIndexs(moduleChildEle);
			} else if ("fields".equals(moduleChildEle.getTagName())) {
				loadFields(moduleChildEle);
			} else if ("topics".equals(moduleChildEle.getTagName())) {
				loadTopicIds(moduleChildEle);
			} else {
				LOG.warn(" unknown node : " + moduleChildEle.getTagName());
			}
		}
	}

	private void loadIndexs(Element element) {
		NodeList children = element.getChildNodes();

		for (int i = 0; i < children.getLength(); i++) {
			Node subNode = children.item(i);
			if (!(subNode instanceof Element)) {
				continue;
			}

			Element subEle = (Element) subNode;
			if ("dir".equals(subEle.getTagName())) {
				indexs.add(((Text) subEle.getFirstChild()).getData());
			}
		}
	}
	
	private void loadTopicIds(Element moduleChildEle) {
		String value = moduleChildEle.getTextContent();
		String[] valueArray = value.split(",");
		topicIds = Arrays.asList(valueArray);
	}

	private void loadFields(Element element) {
		NodeList children = element.getChildNodes();

		for (int i = 0; i < children.getLength(); i++) {
			Node subNode = children.item(i);
			if (!(subNode instanceof Element)) {
				continue;
			}

			Element subEle = (Element) subNode;
			if ("field".equals(subEle.getTagName())) {
				ModuleField field = new ModuleField(subEle);
				this.fields.add(field);
			}
		}
	}

	public Collection<String> getIndexs() {
		return this.indexs;
	}

	public Collection<ModuleField> getFields() {
		return this.fields;
	}
	
	public List<String> getTopicIds() {
		return topicIds;
	}
	
	public String getName() {
		return name;
	}

	public boolean avaliableForTopic(String topicId) {
		return topicIds.contains(topicId);
	}
	
	public ModuleField getField(String fieldName) {
		for (Iterator<ModuleField> iterator = fields.iterator(); iterator
				.hasNext();) {
			ModuleField field = iterator.next();
			if (field.getName().equals(fieldName)) {
				return field;
			}
		}

		return null;
	}
}
