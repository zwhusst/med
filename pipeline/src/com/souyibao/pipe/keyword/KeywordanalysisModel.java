package com.souyibao.pipe.keyword;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class KeywordanalysisModel {
	private String name = null;
	private Collection<String> alias = new ArrayList<String>();
	private String topicId = null;
	private String category = null;
	private String filePath = null;
	private String explanation = null;
		
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<String> getAlias() {
		return alias;
	}

	public String getTopicId() {
		return topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void removeAlias(String data){
		if (alias.contains(data)) {
			alias.remove(data);
		}
	}
	
	public void addAlias(String data) {
		alias.add(data);
	}
		
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public void emptyAlias() {
		this.alias.clear();		
	}
	
	public void aggregateKeyword(){
		for (Iterator<String> iterator = alias.iterator(); iterator.hasNext();){
			String data = iterator.next();
			this.name = this.name + data;
		} 
		
		this.emptyAlias();
	}
}
