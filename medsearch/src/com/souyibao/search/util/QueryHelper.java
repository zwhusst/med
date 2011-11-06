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
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

import com.souyibao.shared.entity.Keyword;

/**
 * 
 * @author Andy
 * <p>
 * Help class to analysis the search text and to create lucene query.
 * </p>
 *
 */
public class QueryHelper {
    
	public static Query createMedQuery(Analyzer analyzer, String field,
			String searchText, Set<Keyword> keywords) {
		BooleanQuery result = new BooleanQuery();
		
		Query query = creatQuery(analyzer, field, searchText, Integer.MAX_VALUE);
		if (query != null) {
			result.add(new BooleanClause(query, BooleanClause.Occur.SHOULD));
		}
		
		if (keywords != null) {
			for (Keyword keyword : keywords) {
				String name = keyword.getName();
				String alias = keyword.getAlias();
				
				query = creatQuery(analyzer, field, name, 0);
				if (query != null) {
					result.add(new BooleanClause(query, BooleanClause.Occur.SHOULD));
				}
				
				if (alias != null) {
					query = creatQuery(analyzer, field, alias, 0);
					if (query != null) {
						result.add(new BooleanClause(query, BooleanClause.Occur.SHOULD));
					}
				} 
			}
		}
		
		return result;
	}
	
	private static Query creatQuery(Analyzer analyzer, String fieldName,
			String queryText, int slop) {
		if ((queryText == null) || (queryText.length() == 0)) {
			return null;
		}
		
		List<String> tokenString = tokenString(analyzer, queryText);
		if (tokenString == null) {
			return null;
		}

		if (tokenString.size() == 1) {
			return createTermQuery(fieldName, tokenString.get(0));
		}

		return creatPhraseQuery(analyzer, fieldName, tokenString, slop);
	}
    
    private static Query createTermQuery(String fieldName, String queryText) {
        Term term = new Term(fieldName, queryText);
        TermQuery query = new TermQuery(term);

        return query; 
    }

	private static Query creatPhraseQuery(Analyzer analyzer, String fieldName,
			Collection<String> tokens, int slop) {
		PhraseQuery query = new PhraseQuery();
		for (String token : tokens) {
			Term term = new Term(fieldName, token);
			query.add(term);
		}
		query.setSlop(slop);

		return query;
	}
    
    private static List<String> tokenString(Analyzer analyzer, String queryText) {
        TokenStream ts = analyzer.tokenStream("queryText", new StringReader(
                queryText));
		CharTermAttribute termAtt = (CharTermAttribute) ts.getAttribute(CharTermAttribute.class);
        
        List<String> result = new ArrayList<String>();        
        try {
            while (ts.incrementToken()) {
                result.add(termAtt.toString());
            }
        } catch (IOException e) {
        }
        
        return result;
    }
}
