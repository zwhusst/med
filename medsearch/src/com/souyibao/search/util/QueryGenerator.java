package com.souyibao.search.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

import com.souyibao.search.module.ModuleFactory;
import com.souyibao.search.module.ModuleField;
import com.souyibao.shared.MedEntityManager;
import com.souyibao.shared.analysis.MedAnalyzer;
import com.souyibao.shared.entity.Keyword;

public class QueryGenerator {
	public static PhraseQuery genPhraseQuery(String field, String phrase) {
		String[] allTerms = parseQueryString(phrase);
		PhraseQuery sloppyPhrase = new PhraseQuery();
		sloppyPhrase.setSlop(0);

		for (int i = 0; i < allTerms.length; i++) {
			sloppyPhrase.add(new Term(field, allTerms[i]));
		}

		return sloppyPhrase;
	}
	
	public static Query genTermQuery(String moduleName, String fieldName,
			String queryTerm) {
		ModuleField moduleField = ModuleFactory.getInstance().getModuleField(
				moduleName, fieldName);

		Term term = new Term(fieldName, queryTerm);

		TermQuery result = new TermQuery(term);
		float boost = (moduleField == null) ? 1.0f : Float
				.parseFloat(moduleField.getBoost());
		result.setBoost(boost);

		return result;
	}

	
	public static Query genTermQuery(ModuleField moduleField,
			String queryTerm) {
		Term term = new Term(moduleField.getName(), queryTerm);

		TermQuery result = new TermQuery(term);
		result.setBoost(Float
				.parseFloat(moduleField.getBoost()));

		return result;
	}
	
	public static String[] parseQueryString(String queryString) {
		MedAnalyzer analyzer = new MedAnalyzer();

		TokenStream tStream = analyzer.tokenStream("query_string",
				new StringReader(queryString));

		Token token = null;
		Collection<String> datas = new ArrayList<String>();
		try {
			while ((token = tStream.next()) != null) {
				datas.add(token.termText());
			}
		} catch (IOException e) {
			throw new RuntimeException("Failed to extract the token");
		}

		return (String[]) datas.toArray(new String[0]);
	}
	
	/**
	 * 
	 * @param queryString
	 * @return
	 */
	public static List<List<String>> getQueryStringRelation(String queryString,
			Set<Keyword> keywords) {
		List<List<String>> result = new ArrayList<List<String>>();
		List<String> andList = new ArrayList<String>();
		Set<Keyword> queryKeywords = new HashSet<Keyword>();

		if ((queryString != null) && (queryString.length() > 0)) {
			String[] splitStrs = queryString.split("[( )、,（），]+");
			// handler the query string first
			for (int i = 0; i < splitStrs.length; i++) {
				String tmp = splitStrs[i];

				Keyword keyword = MedEntityManager.getInstance()
						.getKeywordByName(tmp);
				if (keyword == null) {
					andList.add(tmp);
					result.add(andList);
				} else {
					queryKeywords.add(keyword);
				}
			}
		}
		
		// handle the keyword parameter.
		if (keywords != null) {
			queryKeywords.addAll(keywords);
		}
		
		if (!queryKeywords.isEmpty()) {
			for (Iterator<Keyword> iterator = queryKeywords.iterator(); iterator
					.hasNext();) {
				Keyword keyword = iterator.next();
				
				List<String> orList = new ArrayList<String>();
				orList.add(keyword.getName());
				Collection<String> alias = keyword.getAliasCollection();
				if (alias != null) {
					orList.addAll(alias);
				}
				
				result.add(orList);
			}
		}

		return result;
	}
	
//	public static void main(String[] args) {
//		String test = "感冒 平阳霉素(争光霉素A5,Pingyangmycin,BleomycinA5)";
//		
//		List<List<String>> temp = getQueryStringRelation(test, new String[]{"12", "16", "178", "214"});
//		
//		for (Iterator<List<String>> iterator = temp.iterator(); iterator
//				.hasNext();) {
//			List andList = iterator.next();
//			for (Iterator<String> andIterator = andList.iterator(); andIterator.hasNext();) {
//				System.out.print(andIterator.next() + "  ");
//			}
//			System.out.println("");
//		}
//	}
}
