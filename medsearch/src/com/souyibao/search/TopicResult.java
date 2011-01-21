package com.souyibao.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.souyibao.search.util.Util;
import com.souyibao.shared.entity.Topic;
import com.souyibao.shared.entity.TopicCategory;
import com.souyibao.shared.viewer.IDataProvider;

public class TopicResult {
	private Topic topic = null;
	private TopicCategory category = null;
	private List<IDataProvider> data = null;
	private boolean execeedMaxResult = false;
	
	private String ctxUrl = null;
	
	public TopicResult(){}
	public TopicResult(List<IDataProvider> data){
		this.data = data;
	}
	
	public List<IDataProvider> getData() {
		return data;
	}
	public void setData(List<IDataProvider> data) {
		this.data = data;
	}
	public boolean isExeceedMaxResult() {
		return execeedMaxResult;
	}
	public void setExeceedMaxResult(boolean execeedMaxResult) {
		this.execeedMaxResult = execeedMaxResult;
	}
	
	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	
	public Topic getTopic() {
		return this.topic;
	}
	
	public String getCtxUrl() {
		return ctxUrl;
	}
	public void setCtxUrl(String ctxUrl) {
		this.ctxUrl = ctxUrl;
	}
	public void setCategory(TopicCategory category) {
		this.category = category;
	}

	public boolean isEnableCategoryFilter(){
		Collection<SelectOption> options = MedCacheManager.getInstance()
				.getEnbTopicCategoryWebTree(topic);
		return (!options.isEmpty());
	}
	
	public Collection<SelectOption> getSelectOptions() {
		Collection<SelectOption> options = 
			MedCacheManager.getInstance().getEnbTopicCategoryWebTree(topic);
		
		if (options.isEmpty()) {
			return options;
		}
		
		Collection<SelectOption> result = new ArrayList<SelectOption>();
		// first options is to all
		SelectOption all = Util.formatCategoryFilterVal(""+this.topic.getId(), "0", "全部");
		result.add(all);
		result.addAll(options);
		
		// set the selected option;
		String selectedId = null;
		if (category == null) {
			selectedId = all.getId();
		} else {
			selectedId = Util.getCategoryFilterId(""+ topic.getId(), "" + category.getId());
		}
		
		for (SelectOption option : result) {
			if (option.getId().equals(selectedId)) {
				option.setChecked(true);
			} else {
				option.setChecked(false);
			}
		}

		return result;
	}
}
