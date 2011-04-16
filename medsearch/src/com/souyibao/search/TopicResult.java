package com.souyibao.search;

import java.util.List;

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
}
