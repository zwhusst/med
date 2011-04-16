package com.souyibao.shared.analysis;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class MedAnalyzer extends Analyzer {

	@Override
	public TokenStream tokenStream(String fieldName, Reader reader) {
		return new ChineseTokenStream(reader);
	}

	@Override
	public TokenStream reusableTokenStream(String fieldName, Reader reader)
			throws IOException {
		Tokenizer tokenizer = (Tokenizer) getPreviousTokenStream();
		if (tokenizer == null) {
			tokenizer = new ChineseTokenStream(reader);
			setPreviousTokenStream(tokenizer);
		} else
			tokenizer.reset(reader);
		return tokenizer;
	}
	
	
	public static void main(String[] args) throws Exception {
		MedAnalyzer analyzer = new MedAnalyzer();
		TokenStream tStream = analyzer.tokenStream("contents",
				new StringReader("海归及配偶子女可在contents创业地落户 "));

		output(tStream);
	}
	
	private static void output(TokenStream ts) throws Exception {
		CharTermAttribute termAtt = (CharTermAttribute) ts
				.getAttribute(CharTermAttribute.class);

		while (ts.incrementToken()) {
			System.out.print(termAtt.toString() + " -- ");
		}
		
		System.out.println();
	}
}
