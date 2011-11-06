/** 
* Copyright (c) 2011-2013  上海宜豪健康信息咨询有限公司 版权所有 
* Shanghai eHealth Technology Company. All rights reserved. 

* This software is the confidential and proprietary 
* information of Shanghai eHealth Technology Company. 
* ("Confidential Information"). You shall not disclose 
* such Confidential Information and shall use it only 
* in accordance with the terms of the contract agreement 
* you entered into with Shanghai eHealth Technology Company. 
*/
package com.souyibao.web.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class ResourceServlet  extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9126110894710757129L;

	private static Logger logger = Logger.getLogger(ResourceServlet.class);
	
    protected static final int BUFFER_SIZE = 1024 * 10;

	
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	String resourcePath = req.getPathTranslated(); 
        if (resourcePath == null) {
        	return;
        }

        File resource = new File(resourcePath);
        if (resource.exists()) {
            if (resource.isDirectory()) {
                return;
            }
            sendFile(resource, resp);
        } else {
        	logger.error("no resource found for: " + resourcePath);
        }
    }

    protected void sendFile(File resource, HttpServletResponse resp)
            throws ServletException, IOException {
        InputStream in = null;
        try {
            OutputStream out = resp.getOutputStream();
            in = new FileInputStream(resource);
            byte[] buffer = new byte[BUFFER_SIZE];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
                out.flush();
            }
        } catch (Exception e) {
            throw new ServletException(e);
        } finally {
            if (resp != null) {
                resp.flushBuffer();
            }
            if (in != null) {
                in.close();
            }
        }
    }

}
