package com.souyibao.web.taglib;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import javax.servlet.jsp.JspException;

import com.souyibao.search.SearchResult;
import com.souyibao.shared.entity.Keyword;
import com.souyibao.shared.util.MedUtil;
import com.souyibao.web.util.MedWebUtil;

public class KeywordExplainTag extends MedBaseTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5641577154710401686L;

	private String htmlID = null;

	public String getHtmlID() {
		return htmlID;
	}

	public void setHtmlID(String htmlID) {
		this.htmlID = htmlID;
	}

	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	@Override
	public int doStartTag() throws JspException {
		SearchResult searchResult = getPageData();
		if (searchResult == null) {
			return SKIP_BODY;
		}

		Set<Keyword> keywords = searchResult.getQueryKeywords();
		
		if ((keywords != null) && (!keywords.isEmpty())) {
			StringBuffer data = new StringBuffer();
			data.append("<table");
			if (htmlID != null) {
				data.append(" id=\"").append(htmlID).append("\"");
			}
			data.append(" width=\"100%\" border=\"0\" margin=\"0\" padding=\"0\" cellSpacing=\"0\" cellPadding=\"0\">\n");
			
			// for the top line
			data.append("<tr><td width=\"1%\"><img src=\"img/t1.gif\"></td><td background=\"img/t2.gif\"></td><td width=\"1%\"><img src=\"img/t3.gif\"></td></tr>\n");
			
			// for the content line
			data.append("<tr><td background=\"img/t4.gif\"></td>\n");
			data.append("<td width=\"100%\"><table>");
			data.append("<tr><td class=\"infoSectionGuide\">").append(MedTagUtil.getResource("guide_txt")).append("&nbsp;&nbsp;</td>");
			data.append("<td><table width=\"100%\" border=\"0\" cellPadding=\"0px\">");
			
			StringBuffer allKeywords = new StringBuffer();
			for (Keyword keyword : keywords) {
				String alias = MedUtil.getKeywordAliasText(keyword);
				String brief = MedWebUtil.getKeywrodBrief(keyword);
				if ((alias != null) && (alias.length() > 0)) {
					data.append("<tr><td class=\"highlightkeyword\"><a onmouseout=\"toolTip()\" onmouseover=\"toolTip('")
							.append(alias)
							.append("',forntendColor, backgroundColor)\">")
							.append(keyword.getName()).append("</a></td>\n");
				} else {
					data.append("<tr><td class=\"highlightkeyword\">").append(
							keyword.getName()).append("</a></td>\n");					
				}
				data.append("<td class=\"keywordExpBrief\">").append(brief).append("</td>");
				
				// only the 症状 and 疾病 keywords
				if ((keyword.getTopic().getId() == 1)
						|| (keyword.getTopic().getId() == 4)) {
					// link for the hospital
					String id = "khos_"+ keyword.getId();
					data.append("<td><a id=").append(id);
					data.append(" href=\"javascript:hosList('").append(id).append("')\">");
					
					data.append(MedTagUtil.getResource("hos_related_txt")).append("</a></td></tr>\n");
					
					if (allKeywords.length() != 0) {
						allKeywords.append("|");
					} 
					
					allKeywords.append("" + keyword.getId());
				}
			}
			
			data.append("</table></tr>");
			data.append("</table></td><td background=\"img/t5.gif\"></td></tr>\n");
			
			// for the bottom line	
			data.append("<tr><td width=\"1%\"><img src=\"img/t6.gif\"></td><td background=\"img/t7.gif\"></td><td width=\"1%\"><img src=\"img/t8.gif\"></td></tr></table>\n");
			
			// add one hidden field to page, the value is all the keywords.
			data.append("<input type=\"hidden\" id='all_hos_keys' value=\"")
					.append(allKeywords.toString()).append("\">");
			Writer writer = pageContext.getOut();
			try {
				writer.write(data.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return SKIP_BODY;
	}

	@Override
	public void release() {
		super.release();
	}
}

