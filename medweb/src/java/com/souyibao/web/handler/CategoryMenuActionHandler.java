package com.souyibao.web.handler;

import java.util.Collection;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.souyibao.shared.MedEntityManager;
import com.souyibao.shared.entity.Keyword;
import com.souyibao.shared.entity.TopicCategory;
import com.souyibao.web.model.ActionForward;
import com.souyibao.web.model.ActionForwardMapping;
import com.souyibao.web.model.CategoryMenuData;
import com.souyibao.web.model.IActionHandler;
import com.souyibao.web.model.KeywordCategorysMenu;
import com.souyibao.web.util.MedWebUtil;

public class CategoryMenuActionHandler  implements IActionHandler {

	@Override
	public ActionForward execute(ActionForwardMapping mapping,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String kIds = request.getParameter("ks");

		String[] temp = kIds.split("\\|");
		Set<Keyword> keywords = MedEntityManager.getInstance().getKeysWithIds(temp);	
		
		CategoryMenuData menus = new CategoryMenuData();
		for (Keyword keyword : keywords) {
			KeywordCategorysMenu menu = new KeywordCategorysMenu();
			menu.setKeyword(keyword);
			
			// for the category links
			Collection<TopicCategory> categories = MedWebUtil.getDistinctNameCate(keyword);
			menu.setCategories(categories);
			
			menus.addMenu(menu);
		}
		
		String kid = request.getParameter("k");	
		String cid = request.getParameter("cid");
		
		menus.setKid(kid);
		menus.setCid(cid);
		
		request.setAttribute("categoryMenu", menus);
		
		return mapping.successForward();
	}

}
