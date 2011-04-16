/**
 * Copyright (C) 1999-2010, Intalio Inc.
 *
 * The program(s) herein may be used and/or copied only with
 * the written permission of Intalio Inc. or in accordance with
 * the terms and conditions stipulated in the agreement/contract
 * under which the program(s) have been supplied.
 */
package com.souyibao.search.searcher;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import com.souyibao.search.util.QueryHelper;
import com.souyibao.shared.MedProperties;
import com.souyibao.shared.entity.Keyword;

/**
 * 
 * @author Andy
 *         <p>
 *         Manager class for lucene search. The instance should be created
 *         through {@link SearchManagerFactory} because it is expensive to have
 *         two or more index searcher.
 *         </p>
 */
public class SearchManager {
	private static final Log _logger = LogFactory.getLog(SearchManager.class);

	private IndexSearcher searcher = null;

	private final static String TOP_DOC_NUMBER = "top.doc.number";
	
	private static int _topDocNum = 0;
	static {
		_topDocNum = MedProperties.getInstance().getValueAsInt(TOP_DOC_NUMBER);
	}

	// fild of indexs
	public static final String ID_FIELD = "id";
	public static final String CONTENT_FIELD = "content";
	

	protected SearchManager(IndexSearcher searcher) {
		this.searcher = searcher;
	}

	/**
	 * 
	 * @param analyzer
	 * @param queryText
	 * @param keywords
	 * @return
	 * @throws IOException
	 */
	public Map<String, Float> search(Analyzer analyzer,
			String queryText, Set<Keyword> keywords) throws IOException {

		// index reader check. Reopen the read if it doesn't includes
		// latest index.
		IndexReader reader = searcher.getIndexReader();
		if (!reader.isCurrent()) {
			IndexReader newReader = reader.reopen(true);
			if (reader != newReader) {
				this.searcher.close();
				// need to recreate index search
				this.searcher = new IndexSearcher(newReader);
			}
		}

		Query contentQuery = QueryHelper.createMedQuery(analyzer,
				CONTENT_FIELD, queryText, keywords);

//		DocIdsCollector collector = new DocIdsCollector();

		TopDocs topDocs = searcher.search(contentQuery, null, _topDocNum);

		Map<String, Float> scoreDocValues = new HashMap<String, Float>();
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (int i = 0; i < scoreDocs.length; i++) {
			int docId = scoreDocs[i].doc;
			scoreDocValues.put(searcher.doc(docId).get("id"), scoreDocs[i].score);
		}

		return scoreDocValues;
	}
	
    public void close() {
        try {
            searcher.close();
        } catch (Exception e) {
            _logger.error(
                    "Exception occured when trying to close index searcher.", e);
        }
    }
}
