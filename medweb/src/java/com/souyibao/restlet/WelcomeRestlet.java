package com.souyibao.restlet;

import java.io.IOException;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.MediaType;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.representation.Representation;

import com.souyibao.web.model.RepresentationMeta;
import com.souyibao.web.model.SimpleStringRepresentation;

import freemarker.template.Template;

public class WelcomeRestlet extends BaseRestlet {

	@Override
	public SimpleStringRepresentation processRequest(Request request, Response response) {
		Template template = this.getTemplate();

		Representation representation = new TemplateRepresentation(template,
				MediaType.TEXT_HTML);
		
		// output
		try {
			RepresentationMeta meta = new RepresentationMeta(MediaType.TEXT_HTML.getName(), "UTF-8");
			// output
			return new SimpleStringRepresentation(meta, representation.getText());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}