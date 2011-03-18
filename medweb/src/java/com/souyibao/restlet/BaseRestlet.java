package com.souyibao.restlet;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.apache.log4j.Logger;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.CharacterSet;
import org.restlet.data.MediaType;
import org.restlet.representation.StringRepresentation;

import com.souyibao.cache.CacheManager;
import com.souyibao.cache.CacheManagerFactory;
import com.souyibao.freemarker.ConfigurationContext;
import com.souyibao.web.model.RestletDescriptor;
import com.souyibao.web.model.SimpleStringRepresentation;

import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public abstract class BaseRestlet extends Restlet {
	private static Logger logger = Logger.getLogger(BaseRestlet.class);
	
	private RestletDescriptor restletDescriptor;
	
	private String _cachePath = null;
	
	public BaseRestlet() {
		super();
		
		// check this property name from RestletServlet
		_cachePath = System.getProperty("web_cache_folder");
		
	}
	
	public abstract SimpleStringRepresentation processRequest(Request request, Response response);
	
	@Override
	public void handle(Request request, Response response) {
		SimpleStringRepresentation output = null;
		// find the content from cache;
		String source = request.getResourceRef().getPath();
		String queryStr = request.getResourceRef().getQuery();
		if (queryStr != null) {
			source = source + "?" + queryStr;
		}
		
		CacheManager cacheManager = CacheManagerFactory.getCacheManager(this._cachePath);
		output = cacheManager.readData(source);
		
		if (output == null) {
			// didn't find the data from cache
			output = processRequest(request, response);
			
			// cache it
			cacheManager.cacheData(source, output);
		}
		
		// output
		StringRepresentation result = new StringRepresentation(
				output.getContent(), MediaType.valueOf(output.getMeta().getMediaType()),
				null, new CharacterSet(output.getMeta().getEncoding()));
		
		response.setEntity(result);
	}
	
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
