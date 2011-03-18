package com.souyibao.web.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.restlet.ext.servlet.ServletAdapter;
import org.restlet.routing.Router;

import com.souyibao.cache.CacheManagerFactory;
import com.souyibao.freemarker.ConfigurationContext;
import com.souyibao.restlet.BaseRestlet;
import com.souyibao.web.RestletDescriptorService;
import com.souyibao.web.model.RestletDescriptor;

public class RestletServlet extends HttpServlet {

	private static final long serialVersionUID = -1600029726410534903L;

	private static Logger logger = Logger.getLogger(RestletServlet.class);

	protected ServletAdapter adapter = null;

	@Override
	public synchronized void init() throws ServletException {
		super.init();

		// 1: for the document idx folder
		String idxPath = this.getServletContext().getRealPath("idx");
		System.getProperties().setProperty("document_module_idx", idxPath);
		
		String doctorPath = this.getServletContext().getRealPath("doctoridx");
		System.getProperties().setProperty("doctor_module_idx", doctorPath);
		
		// 2: for the cached folder
		String cachePath = this.getServletContext().getRealPath("cache");
		File cacheFolder = new File(cachePath);
		if ((cacheFolder.exists()) && (!cacheFolder.isDirectory())) {
			logger.info("cache folder is: " + cacheFolder.getAbsolutePath());
		} else {
			cacheFolder.mkdir();
		}
		
		System.getProperties().setProperty("web_cache_folder", cachePath);
		
		// 3: configuration for log4j
		// set the file path for the log
		String logPath = this.getServletContext().getRealPath("logs");
		System.getProperties().setProperty("log4j_path", logPath);
		String prefix =  getServletContext().getRealPath("/");
	    String file = getInitParameter("log4j-init-file");
	    // if the log4j-init-file is not set, then no point in trying
	    if(file != null) {
	    	DOMConfigurator.configure(prefix+file);
	    }
	    
		// 4: init for freemarker configuration
		ConfigurationContext cfgContext = ConfigurationContext.getInstance();
		
        Enumeration initNames = getServletConfig().getInitParameterNames();
        while (initNames.hasMoreElements()) {
        	String name = (String)initNames.nextElement();        	
 
        	if (name.startsWith("freemarker.")) {
        		String fmName = name.substring("freemarker.".length());
        		String fmValue = getServletConfig().getInitParameter(name);
        		
        		
        		cfgContext.setTemplateCfgSettings(fmName, fmValue);
        	}
        }
		
        // templates location
        String templatePath = getServletConfig().getInitParameter("template_path");
        cfgContext.setServletContextForTemplateLoading(getServletContext(), templatePath);
        
		// 5: init the rest url router
		this.adapter = new ServletAdapter(getServletContext());

		// init the router
		Router restletRouter = new Router();
		restletRouter.setRoutingMode(Router.MODE_FIRST_MATCH);

		RestletDescriptorService service = RestletDescriptorService
				.getInstance();

		for (String restletName : service.getRestNames()) {
			RestletDescriptor descriptor = service
					.getRestDescriptorByName(restletName);

			BaseRestlet restletToAdd;

			// assume all the restlets are singleton
			restletToAdd = service.getRestletByName(restletName, descriptor);

			// force regexp init
			for (String urlPattern : descriptor.getUrlPatterns()) {
				restletRouter.attach(urlPattern, restletToAdd);
			}
		}

		adapter.setNext(restletRouter);		
	}

	protected void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");		

		// set the base href attribute
		String baseHref = req.getHeader("Host");

		if (req.getContextPath() != null) {
			if (req.getContextPath().startsWith("/")) {
				baseHref = baseHref + req.getContextPath() + "/";	
			} else {
				baseHref = baseHref + "/"+ req.getContextPath() + "/";
			}
			
		}
		baseHref = req.getScheme() + "://" + baseHref;		
		req.setAttribute("baseHref", baseHref);

		adapter.service(req, res);
	}

	@Override
	public void destroy() {
		super.destroy();
		
		CacheManagerFactory.destoryAllCacheManagers();
	}
	
	
}
