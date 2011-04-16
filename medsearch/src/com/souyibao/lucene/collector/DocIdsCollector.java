package com.souyibao.lucene.collector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.Scorer;

/**
 * 
 * @author Andy
 * 
 * lucene collector, features:
 * 1): Collect all the documents which can be searched by lucene.
 * 2): The doc id maybe out of order
 * 3): The doc isn't ordered by score. 
 * 
 */
public class DocIdsCollector extends Collector{

	private int docBase = 0;
	private List<Integer> docIds = new ArrayList<Integer>();
	
    @Override
    public void setScorer(Scorer scorer) throws IOException {
    }

    @Override
    public void collect(int doc) throws IOException {
    	docIds.add(doc + this.docBase);
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

    public List<Integer> getDocIds() {
    	return this.docIds;
    }
}
