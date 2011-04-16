package com.souyibao.lucene.collector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Scorer;

/**
 * 
 * @author Andy
 * 
 * lucene collector, features:
 * 1): Collect all the documents which can be searched by lucene.
 * 2): The doc id maybe out of order
 * 3): The docs are sorted ordered by score. 
 * 
 */
public class SortedDocIdsCollector extends Collector{

	private List<ScoreDoc> sortedDocs = new ArrayList<ScoreDoc>();
	
	private Scorer scorer = null;
	private int docBase = 0;
	
    @Override
    public void setScorer(Scorer scorer) throws IOException {
    	this.scorer = scorer;
    }

    @Override
    public void collect(int doc) throws IOException {
        ScoreDoc scoreDoc = new ScoreDoc((this.docBase + doc), scorer.score());
        
        sortedDocs.add(scoreDoc);
    }

    @Override
    public void setNextReader(IndexReader reader, int docBase)
            throws IOException {
        this.docBase = docBase;
    }

    @Override
    public boolean acceptsDocsOutOfOrder() {
        return true;
    }
    
    public List<ScoreDoc> getSortedDocs() {
    	Collections.sort(this.sortedDocs, new Comparator<ScoreDoc>(){
			@Override
			public int compare(ScoreDoc o1, ScoreDoc o2) {
				if (o1.score > o2.score) {
					return -1;
				}
				
				if (o1.score < o2.score) {
					return 1;
				}

				return 0;

			}});
    	
    	return this.sortedDocs;
    }

}
