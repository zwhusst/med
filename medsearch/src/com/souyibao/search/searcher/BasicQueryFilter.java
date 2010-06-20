package com.souyibao.search.searcher;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.PhraseQuery;

import com.souyibao.search.module.Module;
import com.souyibao.search.module.ModuleFactory;
import com.souyibao.search.module.ModuleField;
import com.souyibao.search.util.QueryGenerator;
import com.souyibao.shared.entity.Keyword;

/** The default query filter.  Query terms in the default query field are
 * expanded to search the url, anchor and content document fields.*/
public class BasicQueryFilter {

	private static final Log LOG = LogFactory
			.getLog("com.souyibao.search.searcher.BasicQueryFilter");

	private static int SLOP = 0;

	/** Set the maximum number of terms permitted between matching terms in a
	 * sloppy phrase match. */
	public void setSlop(int slop) {
		SLOP = slop;
	}

	public BooleanQuery filter(String module, String queryString, Set<Keyword> keywords,
			BooleanQuery output) {
		Module medModule = ModuleFactory.getInstance().getModule(module);
		if (medModule == null) {
			LOG.fatal("unknown module : " + module);
			throw new RuntimeException(" unknow module : " + module);
		}

		Collection<ModuleField> fields = medModule.getFields();
		List<List<String>> clauseList = QueryGenerator
				.getQueryStringRelation(queryString, keywords);

		for (Iterator<List<String>> iterator = clauseList.iterator(); iterator
				.hasNext();) {
			List<String> andList = iterator.next();

			if (andList.size() == 1) {
				buildClauseQuery(andList.get(0), fields, output);
			} else {
				BooleanQuery orQuery = new BooleanQuery();				
				for (Iterator<String> andIterator = andList.iterator(); andIterator
						.hasNext();) {
					BooleanQuery andQuery = new BooleanQuery();
					
					String clause = andIterator.next();					
					buildClauseQuery(clause, fields, andQuery);
					
					orQuery.add(andQuery, BooleanClause.Occur.SHOULD);
				}
				
				output.add(orQuery, BooleanClause.Occur.MUST);
			}
		}

		return output;
	}

	private void buildClauseQuery(String clause,
			Collection<ModuleField> fields, BooleanQuery output) {
		boolean genPhraseQuery = false;

		String[] terms = QueryGenerator.parseQueryString(clause);

		if ((clause.length() <= 3) && (terms.length != 1)) {
			genPhraseQuery = true;
		}

		if (genPhraseQuery) {
			addSloppyPhrases(terms, fields, output);
		} else {
			for (int i = 0; i < terms.length; i++) {
				BooleanQuery query = new BooleanQuery();

				for (Iterator<ModuleField> iterator = fields.iterator(); iterator
						.hasNext();) {
					ModuleField field = iterator.next();
					query.add(QueryGenerator.genTermQuery(field, terms[i]),
							BooleanClause.Occur.SHOULD);
				}
				output.add(query, BooleanClause.Occur.MUST);
			}
		}
	}

	protected void addSloppyPhrases(String[] tokens,
			Collection<ModuleField> fields, BooleanQuery output) {
		BooleanQuery query = new BooleanQuery();
		for (Iterator<ModuleField> iterator = fields.iterator(); iterator
				.hasNext();) {
			ModuleField field = iterator.next();

			PhraseQuery sloppyPhrase = new PhraseQuery();
			sloppyPhrase.setSlop(SLOP);

			for (int i = 0; i < tokens.length; i++) {
				sloppyPhrase.add(new Term(field.getName(), tokens[i]));
			}
			query.add(sloppyPhrase, BooleanClause.Occur.SHOULD);
		}
		output.add(query, BooleanClause.Occur.MUST);
	}
}
