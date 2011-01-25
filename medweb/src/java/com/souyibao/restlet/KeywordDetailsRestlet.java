package com.souyibao.restlet;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.CharacterSet;
import org.restlet.data.MediaType;
import org.restlet.representation.StringRepresentation;

import com.souyibao.freemarker.KeywordDetailsDataModel;
import com.souyibao.shared.MedEntityManager;
import com.souyibao.shared.entity.Keyword;

public class KeywordDetailsRestlet extends BaseRestlet {
	@Override
	public void handle(Request request, Response response) {
		String keywordid = (String)request.getAttributes().get("keywordid");
		String querykeyword = (String)request.getAttributes().get("querykeyword");
//		String querystring = (String)request.getAttributes().get("querystring");
		String querystring = request.getResourceRef().getQueryAsForm().getFirstValue("qs", null);
		String categoryid = (String)request.getAttributes().get("categoryid");
		String topicid = (String)request.getAttributes().get("topicid");
		
		if (keywordid == null) {
			return;
		}
		
		String[] querykeywordids = null;
		if (querykeyword != null) {
			querykeywordids = querykeyword.split(",");
		}
		
		Keyword keyword = MedEntityManager.getInstance().getKeywordById(keywordid);
		if (keyword == null) {
			return;
		}
		
		if ((querystring != null) && (querystring.length() > 0)) {
			try {
				querystring = URLDecoder.decode(querystring, "UTF-8");
			} catch (UnsupportedEncodingException e) {
			}
		}
		
		KeywordDetailsDataModel dataModel = handleInput(keyword,
				querykeywordids, querystring, categoryid, topicid);
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("detailsData", dataModel);
		String outValue = this.processData(request, data);

		// output
		StringRepresentation output = new StringRepresentation(outValue,
				MediaType.TEXT_HTML, null, new CharacterSet("UTF-8"));
		response.setEntity(output);	
	}
	
	private KeywordDetailsDataModel handleInput(Keyword keyword,
			String[] querykeywordids, String querystring, String categoryid,
			String topicid) {
		// keyword for the explanation
		KeywordDetailsDataModel dataModel = new KeywordDetailsDataModel();
		dataModel.setKeyword(keyword);
		
		// query keywords;
		Collection<Keyword> queryKeywords = new ArrayList<Keyword>();
		if (querykeywordids != null) {
			for (int i = 0; i < querykeywordids.length; i++) {
				Keyword queryKeyword = MedEntityManager.getInstance().getKeywordById(querykeywordids[i]);
				
				if (queryKeyword != null) {
					queryKeywords.add(queryKeyword);
				}
			}
		}
		dataModel.setQuerykeywords(queryKeywords);
		
		dataModel.setUserQuery(querystring);
		dataModel.setCategoryid(categoryid);
		dataModel.setTopicid(topicid);
		
		// keyword explanation
		String explanation = MedEntityManager.getInstance().getKeywordExplanation(keyword);
		dataModel.setExplanation(explanation);
		
		return dataModel;
	}
}
