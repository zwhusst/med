package com.souyibao.web.taglib;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.souyibao.shared.DocToKeywordManager;
import com.souyibao.shared.MedEntityManager;
import com.souyibao.shared.entity.Area;
import com.souyibao.shared.entity.Hospital;
import com.souyibao.shared.entity.Keyword;
import com.souyibao.shared.entity.Topic;
import com.souyibao.shared.entity.TopicCategory;
import com.souyibao.shared.util.MedUtil;
import com.souyibao.shared.viewer.IDataProvider;
import com.souyibao.web.MedWebManager;
import com.souyibao.web.model.AnalysisDocModel;
import com.souyibao.web.model.KeywordToDocCount;
import com.souyibao.web.model.SelectOption;
import com.souyibao.web.util.MedWebUtil;
import com.souyibao.web.util.WebConstants;

public class MedTagUtil {
	private static ResourceBundle m_resource = null;
	
	static {
		InputStream stream = MedTagUtil.class.getClassLoader().getResourceAsStream("Resource.properties");
		try {
			m_resource = new PropertyResourceBundle(stream);
		} catch (IOException e) {
		} 
	}
	
	public static String getResource(String key) {
		return m_resource.getString(key);
	}
	
	public static String getResource(String key, Object[] args) {
		String formatText = getResource(key);

		return MessageFormat.format(formatText, args);
	}
	
	// 名称 | 医院类型 | 医院性质 | 医院等级 | 地址
	public static String getHosListHtml(List<Hospital> hospitals) {
		if (hospitals == null) {
			return "";
		}
		
		StringBuffer result = new StringBuffer();
		for (Hospital hospital : hospitals) {
			String link = "/s?hl=et&t=h&id=" + hospital.getId();
			// name
			result.append("<tr><td class=\"tdLine\"><a href=\"").append(link).append("\" target=\"_blank\">");
			result.append(hospital.getName()).append("</a></td>");
			// type
			result.append("<td class=\"tdLine\">" + hospital.getAssetcharacter() + "</td>");
			// grade
			result.append("<td class=\"tdLine\">" + hospital.getGrade() + "</td>");
			// address
			result.append("<td class=\"tdLine\">" + hospital.getAddress() + "</td>");
			result.append("</tr>");
		}
		
		return result.toString();
	}
	
	public static String getHospitalMenu(String kIds) {
		if ((kIds == null) || (kIds.length() == 0)) {
			return "";
		}
//		<div class="sideMenuPanel sideMenuPanelClosed">
//		<div class="sideMenuPanelTab">军事</div>
//		<div style="height: 0px; display: none;" class="sideMenuPanelContent">
//		<ul>
//			<li id="1013"><a
//				href="http://military.club.china.com/data/threads/1013/1.html"
//				onclick="showforum('http://military.club.china.com','1013')"
//				target="main">中华军备</a></li>
//			<li id="1015"><a
//				href="http://military.club.china.com/data/threads/1015/1.html"
//				onclick="showforum('http://military.club.china.com','1015')"
//				target="main">中华史林</a></li>
//		</ul>
//		</div>
//		</div>
		
		StringBuffer result = new StringBuffer();		
		String[] temp = kIds.split("\\|");
		Set<Keyword> keywords = MedEntityManager.getInstance().getKeysWithIds(temp);
		
		for (Keyword keyword : keywords) {
			result.append("<div class=\"sideMenuPanel sideMenuPanelClosed\">");
			result.append("<div class=\"sideMenuPanelTab\">").append(keyword.getName()).append("</div>");
			result.append("<div style=\"height: 0px; display: none;\" class=\"sideMenuPanelContent\">");
			result.append("<ul>");
			
			// for the category links
			Collection<TopicCategory> categories = getDistinctNameCate(keyword);
			for (TopicCategory category : categories){
				String id = "hCate" + category.getId();
				result.append("<li id=\"").append(id).append("\">");
				result.append("<a href=\"javascript:sHosByCate('").append(category.getId()).append("')\"");
				result.append(">").append(category.getName()).append("</a></li>");
			}
			
			result.append("</ul></div></div>");
		}
//		
//		result.append("<table id=\"cateLinks\">");
//		for (int i = 0; i < temp.length; i++) {
//			TopicCategory category = MedEntityManager.getInstance()
//					.getCategoryById(temp[i]);
//			if (category != null) {
//				String id = "tCat_" + temp[i];
//				result.append("<tr><td id=\"").append(id).append("\" class=\"sel\">");
//				result.append("<a href=\"javascript:sHosByCate('").append(temp[i]).append("')\"");
//				result.append(">").append(category.getName()).append("</a></td></tr>");
//			}
//		}
//		result.append("</table>");
		
		return result.toString();
	}
	
	private static Collection<TopicCategory> getDistinctNameCate(Keyword keyword) {
		Collection<TopicCategory> categories = keyword.getCategories();
		
		Map<String, TopicCategory> result = new HashMap<String, TopicCategory>();
		for (TopicCategory category : categories){
			if (result.get(category.getName()) == null) {
				result.put(category.getName(), category);
			}
		}
		
		return result.values();
	}
	
	/**
	 * 
	 * @param keywordCount
	 * @return
	 */
	public static String getKeywordCountHtmlRow(KeywordToDocCount keywordCount) {
		StringBuffer data = new StringBuffer();

		String keywordId = "" + keywordCount.getKeyword().getId();
		Keyword keyword = MedEntityManager.getInstance().getKeywordById(keywordId);
		String topicName = keyword.getTopic().getName();
		String name = MedWebUtil.getKeywordText(keywordCount.getKeyword());
		int count = keywordCount.getCount();
		Integer idfCount = DocToKeywordManager.getInstance().retrieveIDFMap()
				.get(keywordCount.getKeyword().getId());
		data.append("<TR>\n");
		data.append("<TD>").append(topicName).append("</TD>\n");
		data.append("<TD>").append(keywordId).append("</TD>\n");
		data.append("<TD>").append(name).append("</TD>\n");
		data.append("<TD>").append(count).append("</TD>\n");
		data.append("<TD>")
				.append((idfCount == null) ? 0 : idfCount.intValue()).append(
						"</TD>\n");

		data.append("</TR>\n");

		return data.toString();
	}

	/**
	 * 
	 * @param analysisModel
	 * @return
	 */
	public static String getDocumentHtmlRow(AnalysisDocModel analysisModel) {
		StringBuffer data = new StringBuffer();

		data.append("<TR>\n");
		data.append("<TD>").append(analysisModel.getDocId()).append("</TD>\n");
		data.append("<TD>").append(analysisModel.getDocName())
				.append("</TD>\n");

		StringBuffer displayHtml = new StringBuffer();
		displayHtml.append("<a href=\"javascript:onclick=switchDisplay('")
				.append("docDIV" + analysisModel.getDocId()).append("')\">")
				.append("Content</a>");
		displayHtml.append("<div id=\"").append(
				"docDIV" + analysisModel.getDocId()).append("\"");
		displayHtml.append(" style=\"display: none;\">").append(
				analysisModel.getDocContent()).append("</div>");
		data.append("<TD>").append(displayHtml.toString()).append("</TD>\n");

		Collection<KeywordToDocCount> keywordCounts = analysisModel
				.getKeywordCounts();
		if ((keywordCounts != null) && (!keywordCounts.isEmpty())) {
			StringBuffer strBuf = new StringBuffer();
			strBuf.append("<TD><TABLE border=\"1\">\n");
			strBuf
					.append("<tr><td>Topic</td><td>keywordId</td><td>name</td><td>TF</td><td>IDF</td></tr>\n");

			for (Iterator<KeywordToDocCount> iterator = keywordCounts
					.iterator(); iterator.hasNext();) {
				KeywordToDocCount keywordCount = iterator.next();
				String keywodCountHtml = getKeywordCountHtmlRow(keywordCount);
				strBuf.append(keywodCountHtml);
			}
			strBuf.append("</TABLE></TD>\n");

			data.append(strBuf);
		}

		data.append("</TR>\n");

		return data.toString();
	}
	
	public static String getEditableKeywordHtml(Keyword keyword, boolean showId) {
		StringBuffer data = new StringBuffer();
		data.append("<input type=\"hidden\" name=id value=\"" + keyword.getId() + "\">");
		data.append("<TABLE width=\"100%\">");		
		// id
		if (showId) {
			data.append("<TR><TD>ID:</TD>");
			data.append("<TD>").append(keyword.getId()).append("</TD>");
			data.append("</TR>");
		}
		
		// name
		data.append("<TR><TD>Name:</TD>");
		data.append("<TD>").append(keyword.getId()).append("</TD>");
		data.append("</TR>");
		
		data.append("</TABLE>");
		return null;
	}

	public static String outputSessionData(HttpServletRequest request) {
		StringBuffer output = new StringBuffer();
		
		String[] values = null;
		
		// check for the category filter
		values = request.getParameterValues(WebConstants.PARA_CATA_FILTER);
		if ((values != null) && (values.length > 0)) {
			output.append(getParaValues(WebConstants.PARA_CATA_FILTER, values));
		}
		
		// topic filter
		values = request.getParameterValues(WebConstants.PARA_TOPIC_FILTER);
		if ((values != null) && (values.length > 0)) {
			output.append(getParaValues(WebConstants.PARA_TOPIC_FILTER, values));
		}
		
		// keyword parameter
		values = request.getParameterValues(WebConstants.PARA_KEYWORD);
		if ((values != null) && (values.length > 0)) {
			output.append(getParaValues(WebConstants.PARA_KEYWORD, values));
		}
		
		// handler
		values = request.getParameterValues(WebConstants.PARA_HANDLER);
		if ((values != null) && (values.length > 0)) {
			output.append(getParaValues(WebConstants.PARA_HANDLER, values));
		}
		
		return output.toString();
	}
	
	private static String HTML_KEYWORD_NAME="panekeyword";	
	public static String outputSearchPaneKeyword(HttpServletRequest request) {
		StringBuffer output = new StringBuffer();

		// // <input type="checkbox" name="panekeyword" id="id001"><span id="panekeyword_12121">感冒</span>
		String[] queryKeywordIds = request.getParameterValues("k");
		if (queryKeywordIds != null) {
			for (int i = 0; i < queryKeywordIds.length; i++) {
				String id = queryKeywordIds[i];
				Keyword keyword = MedEntityManager.getInstance().getKeywordById(id);
				if (keyword != null) {
					output.append("<input type=\"checkbox\" checked name=\"").append(HTML_KEYWORD_NAME)
						.append("\" id=\"").append(id).append("\">");
					output.append("<span id=\"panekeyword_").append(id).append("\">")
						.append(keyword.getName()).append("</span>");
				}
			}
		}
		
		return output.toString();
	}
	
	private static String EX_SESSION_DATA_PRX = "ex_";
	
	public static String outputExternalSessionData(HttpServletRequest request) {
		StringBuffer output = new StringBuffer();
		
		String[] values = null;		
		// check the query string		
		values = request.getParameterValues(WebConstants.PARA_QUERY_STRING);
		if ((values != null) && (values.length > 0)) {
			output.append(getParaValues(EX_SESSION_DATA_PRX + WebConstants.PARA_QUERY_STRING, 
					values));
		}
		
		// for the dinput paramter
		values = request.getParameterValues(WebConstants.PARA_DEPRECATE_USER_INPUT);
		if ((values != null) && (values.length > 0)) {
			output.append(getParaValues(EX_SESSION_DATA_PRX + WebConstants.PARA_DEPRECATE_USER_INPUT, 
					values));
		}
		
		// check for the category filter
		values = request.getParameterValues(WebConstants.PARA_CATA_FILTER);
		if ((values != null) && (values.length > 0)) {
			output.append(getParaValues(EX_SESSION_DATA_PRX + WebConstants.PARA_CATA_FILTER, 
					values));
		}
		
		// topic filter
		values = request.getParameterValues(WebConstants.PARA_TOPIC_FILTER);
		if ((values != null) && (values.length > 0)) {
			output.append(getParaValues(EX_SESSION_DATA_PRX + WebConstants.PARA_TOPIC_FILTER, 
					values));
		}
		
		// keyword parameter
		values = request.getParameterValues(WebConstants.PARA_KEYWORD);
		if ((values != null) && (values.length > 0)) {
			output.append(getParaValues(EX_SESSION_DATA_PRX + WebConstants.PARA_KEYWORD, 
					values));
		}
		
		return output.toString();
	}
	
	/**
	 * Get the text for the current searching.
	 * @param request
	 * @return
	 */
	public static String outputCurSearchText(HttpServletRequest request) {
		StringBuffer output = new StringBuffer();
		
		String[] values = null;		
		String deprecateInput = request.getParameter(WebConstants.PARA_DEPRECATE_USER_INPUT);
		if ("0".equals(deprecateInput)) {			
		} else {
			// check the query string		
			values = request.getParameterValues(WebConstants.PARA_QUERY_STRING);
			if ((values != null) && (values.length > 0)) {
				for (int i = 0; i < values.length; i++) {
					output.append(values[i] + "&nbsp;");
				}
			}
		}		
		// check for the keyword
		// keyword parameter
		values = request.getParameterValues(WebConstants.PARA_KEYWORD);
		if ((values != null) && (values.length > 0)) {
			for (int i = 0; i < values.length; i++) {
				String keywordId = values[i];
				Keyword keyword = MedEntityManager.getInstance().getKeywordById(keywordId);
				if (keyword != null) {
					output.append(keyword.getName() + "&nbsp;");
				}
			}
		}
		
		return output.toString();
	}	 
	
	private static String getParaValues(String paraName, String[] values) {
//		String hiddenName = SESSION_DATA_PREFIX + paraName;
		StringBuffer data = new StringBuffer();
		for (int i = 0; i < values.length; i++) {
			data.append("<input type=\"hidden\" name=\"").append(paraName)
					.append("\" value=\"").append(values[i]).append("\"/>\n");
		}
		
		return data.toString();
	}
	
	public static String getWebSearchString(HttpServletRequest request) {
		String queryString = null;
		String deprecateInput = request.getParameter(WebConstants.PARA_DEPRECATE_USER_INPUT);
		if ("0".equals(deprecateInput)) {			
		} else {
			String[] values = request.getParameterValues("q");
			if ((values != null) && (values.length > 0)) {
				for (int i = 0; i < values.length; i++) {
					if (!"".equals(values[i])) {
						queryString = (queryString == null) ? values[i]
								: queryString + " " + values[i];
					}
				}
			}
		}
		
		// for the query keyword ids
		String[] queryKeywordIds = request.getParameterValues("k");		
		if (queryKeywordIds != null) {
			for (int i = 0; i < queryKeywordIds.length; i++) {
				String id = queryKeywordIds[i];
				Keyword keyword = MedEntityManager.getInstance().getKeywordById(id);
				if (keyword != null) {
					queryString = (queryString == null) ? keyword.getName()
							: queryString + " " + keyword.getName();
				}
			}
		}
		
		return queryString;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	public static String getRefineDlgQueryAllURL(HttpServletRequest request) {
		StringBuffer output = new StringBuffer();
		
		// for the query string
		String queryString = request.getParameter("q");
		queryString = (queryString == null) ? "" : queryString;
		
		// for the query keyword
		String explanationid = request.getParameter("eid");
		String[] checkedKeywordIds = request.getParameterValues("k"); 
		
		Set<String> keywords = new HashSet<String>();
		if (explanationid != null) {
			keywords.add(explanationid);
		}
		
		if (checkedKeywordIds != null) {
			for (int i = 0; i < checkedKeywordIds.length; i++) {
				keywords.add(checkedKeywordIds[i]);
			}
		}
		
		String encodedString = "";
		try {
			encodedString = URLEncoder.encode(queryString, "UTF-8");
		} catch (UnsupportedEncodingException e) {			
		}
		
		Collection<String> urlText = new ArrayList<String>();
		urlText.add(queryString);
		
		// for the keyword url and text
		output.append("<a href='s?hl=search&q=").append(encodedString);
		for (Iterator<String> iterator = keywords.iterator(); iterator.hasNext();) {
			String id = iterator.next();
			Keyword keyword = MedEntityManager.getInstance().getKeywordById(id);
			if (keyword != null) {
				urlText.add(keyword.getName());
				output.append("&k=").append(id);
			}
		}
		
		// for the topic filter
		String[] tFilters = request.getParameterValues("tFilter");
		if (tFilters != null) {
			for (int i = 0; i < tFilters.length; i++) {
				output.append("&tFilter=").append(tFilters[i]);
			}
		}
		
		// for the category filters
		String[] cFilters = request.getParameterValues("cFilter");
		if (cFilters != null) {
			for (int i = 0; i < cFilters.length; i++) {
				output.append("&cFilter=").append(cFilters[i]);
			}
		}
		
		// for the url text
		output.append("'>").append(MedTagUtil.getResource("query_txt"));
		boolean isFirst = true;
		for (Iterator<String> iterator = urlText.iterator(); iterator.hasNext();) {
			String text = iterator.next();
			if (text != null) {
				if (isFirst) {
					output.append("&nbsp;<b>").append(text).append("</b>&nbsp;");
					isFirst = false;
				} else {
					output.append("和&nbsp;<b>").append(text).append("</b>&nbsp;");
				}
			}
		}
		
		output.append("</a>");		
		return output.toString();
	}
	
	public static String getRefineDlgKeywordQueryURL(HttpServletRequest request) {
		StringBuffer output = new StringBuffer();
		
		// for the query string
		String queryString = request.getParameter("q");
		queryString = (queryString == null) ? "" : queryString;
		
		// for the query keyword
		String explanationid = request.getParameter("eid");
		
		Keyword keyword = null;
		
		String encodedString = "";
		try {
			encodedString = URLEncoder.encode(queryString, "UTF-8");
		} catch (UnsupportedEncodingException e) {			
		}
		
		// dinput parameter means the query parameter won't be used in the query
		output.append("<a href='s?hl=search&dinput=0&q=").append(encodedString);
		if (explanationid != null) {
			keyword = MedEntityManager.getInstance().getKeywordById(
					explanationid);
			if (keyword != null) {
				output.append("&k=").append(explanationid);
			}
		}
		
		// for the topic filter
		String[] tFilters = request.getParameterValues("tFilter");
		if (tFilters != null) {
			for (int i = 0; i < tFilters.length; i++) {
				output.append("&tFilter=").append(tFilters[i]);
			}
		}
		
		// for the category filters
		String[] cFilters = request.getParameterValues("cFilter");
		if (cFilters != null) {
			for (int i = 0; i < cFilters.length; i++) {
				output.append("&cFilter=").append(cFilters[i]);
			}
		}
		
		// for the url text
		output.append("'>").append(MedTagUtil.getResource("query_only_txt"));
		if (keyword != null) {
			output.append("&nbsp;<b>").append(keyword.getName()).append("</b>&nbsp;");
		}
		
		output.append("</a>");		
		return output.toString();		
	}
	
	/**
	 * Get the TD html for one keyword
	 * 
	 * @param kWeight
	 * @return
	 */
	public static String getKeywordTDHtml(IDataProvider provider){
		StringBuffer result = new StringBuffer();
		
		result.append("<TD class=\"tdLine\" width=\"70%\">");
		String id = "key_"+ provider.getId();
		result.append("<a id=").append(id);
		result.append(" href=\"javascript:dlg('").append(id)
				.append("','").append(provider.getTopicId()).append("','")
				.append(provider.getId()).append("','")
				.append(provider.getName()).append("')\"");
		String aliasText = provider.getAlias();	
		result.append(" onMouseOver=\"tip('").append(id).append("','")
				.append(provider.getTopicId()).append("','")
				.append(aliasText).append("',").append("fc, bc")
				.append(")\" onMouseOut=\"tip('").append(id).append("')\"");
		result.append(">");
		result.append(provider.getName()).append(
				"</a></TD>\n");
				
		return result.toString();
	}
	
	public static String getCategoryHtml(Topic topic,
			Map<String, String> categoryFilters, String tagName) {
		Collection<SelectOption> options = MedWebManager.getInstance()
				.getEnbTopicCategoryWebTree(topic);
		String tagStart = "<" + tagName;
		String tagEnd = "</" + tagName + ">";
		if ((options == null) || (options.isEmpty())) {
			// no category has been defined this topic
			return tagStart + " class=\"tdTit_g\">&nbsp;" + tagEnd;
		}
		
		SelectOption selectedOption = null; 
		boolean showAll= false;
		StringBuffer outData = new StringBuffer();
		
		// put the parameter first;
		String parameterId = Constants.CATEGOR_FILTER_PREFIX + topic.getId();					
		outData.append(tagStart + " class=\"tdTit_g\"><select id=\"").append(
				parameterId).append(
				"\" align=\"right\" onChange=\"categoryChange(").append(")\">");
		
		if (MedEntityManager.TOPIC_DOCTOR_ID.equals(""+topic.getId())) {
			Area area = MedEntityManager.getInstance().getAreaById(categoryFilters.get(topic.getId()+""));
			selectedOption = MedWebUtil.formatCategoryFilterVal(topic, area);
		} else {
			TopicCategory filterCategory = null;
			if (categoryFilters != null) {
				String cid = categoryFilters.get("" + topic.getId());
				filterCategory = MedEntityManager.getInstance().getCategoryById(cid);			
			}
			
			// get the selected category
			if (filterCategory == null) {
				filterCategory = MedEntityManager.getInstance().getPrefCateByTopic(topic);		
			}
			showAll = (MedEntityManager.ROOT_CATEGORY_ID == filterCategory.getId());
			

			// for the "show all" option
			SelectOption rootOption = MedWebUtil.formatCategoryFilterVal(topic, MedEntityManager.getInstance().getRootCategory());
			if (showAll) {
				outData.append("<option value=\"").append(rootOption.getId())
						.append("\" selected>");
			} else {			
				outData.append("<option value=\"").append(rootOption.getId())
						.append("\">");
			}
			outData.append(rootOption.getValue());
			
			// for all the other options		
			if (showAll) {
				selectedOption = rootOption;
			} else {
				selectedOption = MedWebUtil.formatCategoryFilterVal(topic, filterCategory);
			}
		}
		
		for (Iterator<SelectOption> iterator = options.iterator(); iterator
				.hasNext();) {
			SelectOption option = iterator.next();
			if (option.isEnabled()) {
				if ((!showAll) && (selectedOption.getId().equals(option.getId()))) {
					outData.append("<option value=\"").append(option.getId())
							.append("\" selected>");
				} else {
					outData.append("<option value=\"").append(option.getId())
							.append("\">");
				}
				
				for (int i = 0; i < option.getLevel(); i++) {
					outData.append("&nbsp;&nbsp;&nbsp;");
				}
				outData.append(option.getValue());
			}
		}
		
		outData.append("</select>" + tagEnd);
		return outData.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(MedTagUtil.getResource("andy.test")); // test.para
		
		System.out.println(MedTagUtil.getResource("test.para", new String[]{"0000", "111"}));
	}
	
}
