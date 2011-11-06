/** 
* Copyright (c) 2011-2013  上海宜豪健康信息咨询有限公司 版权所有 
* Shanghai eHealth Technology Company. All rights reserved. 

* This software is the confidential and proprietary 
* information of Shanghai eHealth Technology Company. 
* ("Confidential Information"). You shall not disclose 
* such Confidential Information and shall use it only 
* in accordance with the terms of the contract agreement 
* you entered into with Shanghai eHealth Technology Company. 
*/
package com.souyibao.search.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;

import com.souyibao.shared.DocToKeywordManager;
import com.souyibao.shared.MedEntityManager;
import com.souyibao.shared.entity.Keyword;
import com.souyibao.shared.entity.Topic;
import com.souyibao.shared.entity.TopicCategory;
import com.souyibao.shared.viewer.IDataProvider;
import com.souyibao.shared.viewer.KeywordDataProvider;

public class SearchUtil {

	public static List<IDataProvider> filterKWWithCategory(List kws, 
			TopicCategory category) {
		if (category == null) {
			return kws;
		}
		if (category.getId() == MedEntityManager.ROOT_CATEGORY_ID) {
			return kws;
		}
		
		List result = new ArrayList();
		for(Iterator kw = kws.iterator(); kw.hasNext();) {
			KeywordDataProvider kprovider = (KeywordDataProvider)kw.next();
			Keyword keyword = kprovider.getKeyword();
			if (MedEntityManager.getInstance().isRightFilterKeyword(keyword, category)) {
				result.add(kprovider);
			}
		}
		
		return result;
	}
	
	public static List<KeywordDataProvider> retriveKeywordDataProvider(
			Map<String, Float> scoreDocValues)
			throws CorruptIndexException, IOException {
		List<KeywordDataProvider> keywordProviders = new ArrayList<KeywordDataProvider>();

		DocToKeywordManager doc2KeywordManager = DocToKeywordManager.getInstance();
		for (String docId : scoreDocValues.keySet()) {
			List<KeywordDataProvider> keywords = doc2KeywordManager.getDocKeywords(docId);

			if (keywords != null) {
				float baseWeight = scoreDocValues.get(docId);
				for (KeywordDataProvider provider : keywords) {
					provider.updateTotalWeight(baseWeight);
				}
				
				keywordProviders.addAll(keywords);
			}
		}

		return keywordProviders;
	}
	
	public static Set<Keyword> getKeywordByQryString(String qry) {
		Set<Keyword> result = new HashSet<Keyword>();
		if (qry != null) {
			String[] splitWords = qry.split("[( )、,（），]+\"");
			for (int i = 0; i < splitWords.length; i++) {
				Keyword keyword = MedEntityManager.getInstance()
						.getKeywordByName(splitWords[i]);
				if (keyword != null) {
					result.add(keyword);
				}
			}
		}
	
		return result;
	}
	
	
	public static Set<Keyword> getKeywordByQryData(String qry,
			Set<Keyword> keywords) {
		Set<Keyword> result = getKeywordByQryString(qry);
		
		if ((keywords != null) && (!keywords.isEmpty())) {
			result.addAll(keywords);
		}
		
		return result;
	}
	
	public static Collection<KeywordDataProvider> calKeywordWeight(
			List<KeywordDataProvider> docKeywords, Set<Topic> topicsFilter) {
		if (docKeywords == null) {
			return null;
		}
		Map<Keyword, KeywordDataProvider> keywordMap = new HashMap<Keyword, KeywordDataProvider>();

		for (KeywordDataProvider docKeyword : docKeywords) {
			Topic topic = docKeyword.getKeyword().getTopic();
			
			if (topicsFilter != null) {
				if (!topicsFilter.contains(topic)) {
					continue;
				}
			}
			
			KeywordDataProvider grpWeight = keywordMap.get(docKeyword.getKeyword());
			
			if (grpWeight == null) {				
				grpWeight = docKeyword.newObj();
				keywordMap.put(docKeyword.getKeyword(), grpWeight);
			} else {
				grpWeight.addWeight(docKeyword.getTotalWeight());
			}
		}

		return keywordMap.values();
	}

	public static Map<Topic, List<KeywordDataProvider>> groupKeywordByTopic(
			Collection<KeywordDataProvider> keywordWeights, Set<Keyword> qKeywords) {
		
		if (keywordWeights == null) {
			return null;
		}
		Map<Topic, List<KeywordDataProvider>> topicKeywordMap = new HashMap<Topic, List<KeywordDataProvider>>();

		for (KeywordDataProvider hitKeyword: keywordWeights) {
			Topic topic = hitKeyword.getKeyword().getTopic();
			List keywordList = topicKeywordMap.get(topic);

			if (keywordList == null) {
				keywordList = new ArrayList<KeywordDataProvider>();
				topicKeywordMap.put(topic, keywordList);
			}

			keywordList.add(hitKeyword);
		}
		keywordWeights.clear();
		keywordWeights = null;

		// sort the lists by the weight		
		Set<Topic> allTopic = topicKeywordMap.keySet();

		for (Topic topic : allTopic) {
			List<KeywordDataProvider> datas = topicKeywordMap.get(topic);
			Collections.sort(datas, new WeightComparator(qKeywords));
		}
		
		return topicKeywordMap;

	}

	static class WeightComparator implements Comparator<KeywordDataProvider> {

		private Set<Keyword> hPKeywords = null;
		
		public WeightComparator(Set<Keyword> hPKeywords) {
			this.hPKeywords = hPKeywords;
		}
		
		@Override
		public int compare(KeywordDataProvider o1, KeywordDataProvider o2) {
			if (hPKeywords.contains(o1.getKeyword())) {
				return -1;
			}
			if (hPKeywords.contains(o2.getKeyword())) {
				return 1;
			}

			if (o1.getTotalWeight() > o2.getTotalWeight()) {
				return -1;
			}

			if (o1.getTotalWeight() < o2.getTotalWeight()) {
				return 1;
			}

			return 0;
		}
	}
}
