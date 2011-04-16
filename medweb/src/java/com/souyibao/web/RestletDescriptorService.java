package com.souyibao.web;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.souyibao.restlet.BaseRestlet;
import com.souyibao.web.model.RestletDescriptor;

public class RestletDescriptorService {

	private static Logger logger = Logger.getLogger(RestletDescriptorService.class);
	
	private static RestletDescriptorService instance = new RestletDescriptorService();
	
	// restlet descriptor name -> descriptor self
	private Map<String, RestletDescriptor> nameToDescriptor = new HashMap<String, RestletDescriptor>();
	
	private RestletDescriptorService() {
		loadData();
	}

	public static RestletDescriptorService getInstance() {
		return instance;
	}
	
	private void loadData() {
		URL resourceURL = RestletDescriptorService.class.getClassLoader().getResource(
				"rest-contrib.xml");
		
		Document doc = null;

		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			doc = builder.parse(resourceURL.toString());
		} catch (Exception e) {
			throw new RuntimeException(
					"Exception occured in loading restlet contribution file : rest-contrib.xml");
		}

		if (doc == null) {
			throw new RuntimeException(
					"Exception occured in parse restlet contribution file : rest-contrib.xml");
		}

		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		
		Element root = doc.getDocumentElement();
		NodeList handlerNodeList = root.getChildNodes();
		for (int i = 0; i < handlerNodeList.getLength(); i++) {
			Node handlerNode = handlerNodeList.item(i);
			if (!(handlerNode instanceof Element))
				continue;

			Element descriptorEle = (Element) handlerNode;
			if ("restlet".equals(descriptorEle.getTagName())) {
				RestletDescriptor descriptor = new RestletDescriptor(); 
				
				// name
				descriptor.setName(descriptorEle.getAttribute("name"));
				// class
				Class<BaseRestlet> restletClass = null;
				try {
					restletClass = (Class<BaseRestlet>)loader.loadClass(descriptorEle.getAttribute("class"));
					descriptor.setClassName(restletClass);
				} catch (ClassNotFoundException e) {
					logger.error(e);
				}
				// enabled
				String enabled = descriptorEle.getAttribute("enabled");
				descriptor.setEnabled(enabled.equalsIgnoreCase("true")? true: false);
				
				// template
				String template = descriptorEle.getAttribute("template");
				descriptor.setTemplate(template);
				
				// urlPatterns
				// load url patterns
				NodeList patternNode = descriptorEle.getElementsByTagName("urlPatterns");
				parseUrlPatterns(descriptor, patternNode);
				
				Object oldValue = this.nameToDescriptor.put(descriptor.getName(), descriptor);
				if (oldValue != null) {
					logger.error("two restlets have the same name: " + descriptor.getName());
				}
			}
		}
	}
	
	
	private void parseUrlPatterns(RestletDescriptor descriptor, NodeList patternList) {
		NodeList subNodes = null;
		if ((patternList != null) && (patternList.getLength() > 0)) {
			subNodes = patternList.item(0).getChildNodes();
		}
		
		for (int i = 0; i < subNodes.getLength(); i++) {
			Node subNode = subNodes.item(i);
			if (!(subNode instanceof Element))
				continue;
			
			Element patternEle = (Element) subNode;
			if ("urlPattern".equals(patternEle.getTagName())) {
				descriptor.addUrlPattern(patternEle.getTextContent());
			}
		}
	}
	
	public Collection<String> getRestNames() {
		return this.nameToDescriptor.keySet();
	}
	
	public RestletDescriptor getRestDescriptorByName(String name) {
		return this.nameToDescriptor.get(name);
	}
	
	public BaseRestlet getRestletByName(String name, RestletDescriptor descriptor) {
        if (nameToDescriptor.containsKey(name)) {

        	RestletDescriptor rpd = nameToDescriptor.get(name);
            Class<BaseRestlet> theClass = rpd.getClassName();
            if (theClass == null) {
            	logger.error("Error while creating Restlet instance. Class not available for restlet descriptor: "
                        + name);
                return null;
            }
            
            BaseRestlet restlet;
            try {
                restlet = theClass.newInstance();
            } catch (InstantiationException e) {
            	logger.error("Error while creating Restlet instance : " + e.getMessage());
                return null;
            } catch (IllegalAccessException e) {
            	logger.error("Error while creating Restlet instance : " + e.getMessage());
                return null;
            }
            restlet.setRestletDescriptor(descriptor);
            
            return restlet;
        } else {
        	logger.error("Restlet " + name + " is not registred");
            return null;
        }
	}
	
	public static void main(String[] args) throws Exception {
		RestletDescriptorService.getInstance();
	}

}
