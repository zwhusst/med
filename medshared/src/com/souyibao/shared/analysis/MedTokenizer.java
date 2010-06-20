package com.souyibao.shared.analysis;

import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStreamReader;

import java.io.Reader;

import java.io.StringReader;

import org.apache.lucene.analysis.Token;

import org.apache.lucene.analysis.Tokenizer;

public final class MedTokenizer extends Tokenizer implements
		MedAnalysisConstants {

	private MedAnalysisTokenManager tokenManager;

	/** Construct a tokenizer for the text in a Reader. */
	public MedTokenizer(Reader reader) {
		super(reader);
		tokenManager = new MedAnalysisTokenManager(reader);
	}

	/** Returns the next token in the stream, or null at EOF. */
	public final Token next() throws IOException {
		com.souyibao.shared.analysis.Token t;

		try {
			loop: {
				while (true) {
					t = tokenManager.getNextToken();
					switch (t.kind) { // skip query syntax tokens
					case EOF:
					case WORD:
					case ACRONYM:
					case SIGRAM:
						break loop;
					default:
					}
				}
			}
		} catch (TokenMgrError e) { // translate exceptions
			throw new IOException("Tokenizer error:" + e);
		}

		if (t.kind == EOF) // translate tokens
			return null;
		else {
			return new Token(t.image, t.beginColumn, t.endColumn,
					tokenImage[t.kind]);
		}
	}

	/** For debugging. */

	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			System.out.print("Text: ");
			String line = in.readLine();
			Tokenizer tokenizer = new MedTokenizer(new StringReader(line));

			Token token;
			System.out.print("Tokens: ");

			while ((token = tokenizer.next()) != null) {
				System.out.print(token.termText());
				System.out.print(" ");
			}

			System.out.println();
		}
	}
}
