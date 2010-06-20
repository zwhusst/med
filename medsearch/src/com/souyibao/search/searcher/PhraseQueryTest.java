package com.souyibao.search.searcher;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.RAMDirectory;

import com.souyibao.shared.analysis.MedAnalyzer;

public class PhraseQueryTest {
	public static void main(String[] args) throws Exception {
		RAMDirectory directory = new RAMDirectory();
		IndexWriter writer = new IndexWriter(directory, new MedAnalyzer(), true);
		Document doc = new Document();
		doc.add(new Field("name", "急性鼻炎(伤风、感冒)", Field.Store.YES, Field.Index.TOKENIZED));
		writer.addDocument(doc);
		writer.close();
		IndexSearcher searcher = new IndexSearcher(directory);
		
//		query.add(termQuery(new Term("name", "感冒"), "20"),
//				BooleanClause.Occur.SHOULD);
//		TermQuery query = new TermQuery(new Term("name", "感冒"));
		
		// --------------------------------------------------
		PhraseQuery query = new PhraseQuery();
		query.setSlop(3);
		
		query.add(new Term("name", "急"));
		query.add(new Term("name", "性"));
		query.add(new Term("name", "鼻"));
		query.add(new Term("name", "炎"));
		// --------------------------------------------------		
		System.out.println("query : " +query );
		Hits hits = searcher.search(query, null, (Sort)null);
		
		System.out.println("hit length : " + hits.length());
	}
	

	private static Query termQuery(org.apache.lucene.index.Term term, String boost) {
		TermQuery result = new TermQuery(term);
		result.setBoost(Float.parseFloat(boost));
		
		return result;
	}
}
