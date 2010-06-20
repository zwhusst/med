package com.souyibao.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.souyibao.search.util.Util;
import com.souyibao.shared.entity.Topic;
import com.souyibao.shared.viewer.IDataProvider;

public class TopicResult {
	private Topic topic = null;
	private String filterId = null;
	private List<IDataProvider> result = null;
	private boolean execeedMaxResult = false;
	
	public TopicResult(){}
	public TopicResult(List<IDataProvider> data){
		this.result = data;
	}
	
	public List<IDataProvider> getResult() {
		return result;
	}
	public void setResult(List<IDataProvider> result) {
		this.result = result;
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
	
	public void setFilterId(String filterId) {
		this.filterId = filterId;
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
		if (filterId == null) {
			selectedId = all.getId();
		} else {
			selectedId = Util.getCategoryFilterId(""+ topic.getId(), filterId);
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
