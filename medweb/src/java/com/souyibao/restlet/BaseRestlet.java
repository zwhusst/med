package com.souyibao.restlet;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.apache.log4j.Logger;
import org.restlet.Request;
import org.restlet.Restlet;

import com.souyibao.freemarker.ConfigurationContext;
import com.souyibao.web.model.RestletDescriptor;

import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class BaseRestlet extends Restlet {
	private static Logger logger = Logger.getLogger(BaseRestlet.class);
	private RestletDescriptor restletDescriptor;
	
	public RestletDescriptor getRestletDescriptor() {
		return restletDescriptor;
	}

	public void setRestletDescriptor(RestletDescriptor restletDescriptor) {
		this.restletDescriptor = restletDescriptor;
	}

	public Template getTemplate() {
		String templateName = restletDescriptor.getTemplate();
		
		try {
			return ConfigurationContext.getInstance().getTemplate(templateName);
		} catch (IOException e) {
			logger.error("Failed to find template " + templateName, e);
		}
		
		return null;
	}
	
	public String processData(Request request, Map<String, Object> data) {
		Template template = this.getTemplate();
		
		// data from the input parameters
		StringWriter out = new StringWriter();
		SimpleHash root = new SimpleHash();
		root.putAll(data);
		
		// data from the request attributes;
		if (request.getAttributes() != null) {
			root.putAll(request.getAttributes());
		}
		
		try {
			template.process(root, out);
		} catch (TemplateException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}
		
		return out.getBuffer().toString();
	}
}
