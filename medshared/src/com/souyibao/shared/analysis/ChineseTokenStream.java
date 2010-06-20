package com.souyibao.shared.analysis;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.Tokenizer;

public class ChineseTokenStream extends Tokenizer {
	// already segmented tokens
	List<Token> tokenBuffer = new ArrayList<Token>();
	int nextToken = 0;
	
	// already read from input, but not parsed non Chinese
	StringBuffer characterBuffer = new StringBuffer();
	boolean chineseInBuffer = false;
	int characterBufferStart = 0;  // start position in reader
	
	ChineseSegmenter seg = ChineseSegmenter.getInstance();
	
	public ChineseTokenStream(Reader input) {
		super(input);
	}
	
	@Override
	public void reset() throws IOException {
		tokenBuffer.clear();
		nextToken = 0;
		characterBuffer.setLength(0);
		chineseInBuffer = false;
		super.reset();
	}
	
	@Override
	public void reset(Reader input) throws IOException {
		tokenBuffer.clear();
		nextToken = 0;
		characterBuffer.setLength(0);
		chineseInBuffer = false;
		super.reset(input);
	}

	@Override
	public Token next() throws IOException {
		// first try to see whether we have previous segmented tokens
		if (nextToken < tokenBuffer.size()) {
			return tokenBuffer.get(nextToken++);
		}
		nextToken = 0;
		tokenBuffer.clear();
		
		// read from input
		int c;
		while ( (c = input.read()) != -1) {
			char ch = (char) c;
			if (isSeparator(ch)) {
				if (characterBuffer.length() > 0) {
					tokenizeBuffer();
				} else {
					continue;
				}
			} else {
				boolean isCJK = isCJK(ch);
				if (characterBuffer.length() == 0) {
					characterBuffer.append(ch);
					chineseInBuffer = isCJK;
				} else if (chineseInBuffer == isCJK) {
					characterBuffer.append(ch);
				} else {
					tokenizeBuffer();
					characterBuffer.append(ch);
					chineseInBuffer = isCJK;
				}
			}
			if (nextToken < tokenBuffer.size()) {
				return tokenBuffer.get(nextToken++);
			} else {
				continue;
			}
		}
		if (characterBuffer.length() > 0) {
			tokenizeBuffer();
		}
		if (nextToken < tokenBuffer.size()) {
			return tokenBuffer.get(nextToken++);
		} else {
			return null;
		}
	}
	
	boolean isSeparator(char c) {
		return !Character.isLetterOrDigit(c);
	}
	
	boolean isCJK(char c) {
//	       "\u3040"-"\u318f",
//	       "\u3300"-"\u337f",
//	       "\u3400"-"\u3d2d",
//	       "\u4e00"-"\u9fff",
//	       "\uf900"-"\ufaff"
		return c > 255;  // FIXME: 
	}
	
	// tokenize the characterBuffer, and give result into tokenBuffer
	void tokenizeBuffer() {
		if (!chineseInBuffer) {
			tokenBuffer.add(new Token(characterBuffer.toString(), -1, -1));
		} else {
			char[] text = new char[characterBuffer.length()];
			characterBuffer.getChars(0, text.length, text, 0);
			List<String> result = new ArrayList<String>();
			seg.segmentNormalizedString(characterBuffer, result);
			for (Iterator<String> it = result.iterator(); it.hasNext();) {
				String s = it.next();
				tokenBuffer.add(new Token(s, -1, -1));
			}
		}
		characterBuffer.setLength(0);
		chineseInBuffer = false;
	}
	
	// testing main method
	public static void main(String[] args) throws Exception {
		Reader r = new InputStreamReader(new FileInputStream("c:\\temp.txt"), "UTF8");
		ChineseTokenStream stream = new ChineseTokenStream(r);
		Token t;
		while ( (t = stream.next()) != null) {
			System.out.println(t.termText());
		}
	}
}
