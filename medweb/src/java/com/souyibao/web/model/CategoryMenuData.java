package com.souyibao.web.model;

import java.util.ArrayList;
import java.util.Collection;

public class CategoryMenuData {
	private String kid = null;
	private String cid = null;
	
	private Collection<KeywordCategorysMenu> menus = new ArrayList<KeywordCategorysMenu>();

	public Collection<KeywordCategorysMenu> getMenus() {
		return menus;
	}

	public void setMenus(Collection<KeywordCategorysMenu> menus) {
		this.menus = menus;
	}
	
	public void addMenu(KeywordCategorysMenu menu) {
		menus.add(menu);
	}

	public String getKid() {
		return kid;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}
}
