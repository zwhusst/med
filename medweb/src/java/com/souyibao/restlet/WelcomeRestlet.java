package com.souyibao.restlet;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.MediaType;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.representation.Representation;

import freemarker.template.Template;

public class WelcomeRestlet extends BaseRestlet {

	@Override
	public void handle(Request request, Response response) {
		Template template = this.getTemplate();
		
		Representation representation = new TemplateRepresentation(template, MediaType.TEXT_HTML);
		response.setEntity(representation);
	}
}