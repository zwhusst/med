package com.souyibao.freemarker;

import com.souyibao.shared.entity.Keyword;

public class SearchPaneKeyword {
	private Keyword keyword = null;
	private boolean checked = false;
	
	public SearchPaneKeyword(Keyword keyword) {
		this.keyword = keyword;
	}
	
	public Keyword getKeyword() {
		return keyword;
	}
	public void setKeyword(Keyword keyword) {
		this.keyword = keyword;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	
}
