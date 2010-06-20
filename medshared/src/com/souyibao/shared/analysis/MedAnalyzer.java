package com.souyibao.shared.analysis;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

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
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			System.out.print("Text: ");
			String line = in.readLine();
			MedAnalyzer analyzer = new MedAnalyzer();
			TokenStream tStream = analyzer.tokenStream("contents",
					new StringReader(line));

			Token token;
			System.out.print("Tokens: ");
			while ((token = tStream.next()) != null) {
				System.out.print(token.termText());
				System.out.print(" ");
			}
			System.out.println();
		}
	}
}
