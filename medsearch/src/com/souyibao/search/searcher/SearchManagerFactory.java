/**
 * Copyright (C) 1999-2010, Intalio Inc.
 *
 * The program(s) herein may be used and/or copied only with
 * the written permission of Intalio Inc. or in accordance with
 * the terms and conditions stipulated in the agreement/contract
 * under which the program(s) have been supplied.
 */
package com.souyibao.search.searcher;

import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.SimpleFSDirectory;

import com.souyibao.shared.analysis.MedAnalyzer;
import com.souyibao.shared.entity.Keyword;

/**
 * 
 * @author Andy
 * <p>
 * Factory class to create index searcher {@link SearchManager}
 * </p>
 */
public class SearchManagerFactory {
	private static final Log _logger = LogFactory.getLog(SearchManagerFactory.class);
    
    private static ConcurrentMap<String, SearchManager> _searchManagers = 
        new ConcurrentHashMap<String, SearchManager>();
    
	/**
	 * return a lists of document id values
	 * 
	 * @param indexFolderPath
	 * @param oid
	 * @param queryText
	 * @param keywords
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Float> search(String indexFolderPath,
			String queryText, Set<Keyword> keywords)
            throws Exception {
        // get the search manager
        SearchManager searchManager = getSearchManager(indexFolderPath);

        return searchManager.search(new MedAnalyzer(), queryText, keywords);
    }

    /**
     * 
     * Get the search manager according to index path
     * 
     * @param indexFolderPath
     * @return
     * @throws Exception
     */
    public static SearchManager getSearchManager(String indexFolderPath)
            throws Exception {
        SearchManager searchManager = _searchManagers.get(indexFolderPath);
        try {
            if (searchManager == null) {
                IndexSearcher searcher = new IndexSearcher(
                        new SimpleFSDirectory(new File(indexFolderPath)), true);
                
                searchManager = new SearchManager(searcher);
                _searchManagers.put(indexFolderPath,
                        searchManager);
            }
        } catch (Exception e) {
            _logger.error("Failed to create index searcher.", e);
            throw e;
        }

        return searchManager;
    }
    
    public static void close() {
        for (SearchManager searchManager : _searchManagers.values()) {
            searchManager.close();
        }

        _searchManagers.clear();
    }
}
