/** 
* Copyright (c) 2011-2013  上海宜豪健康信息咨询有限公司 版权所有 
* Shanghai eHealth Technology Company. All rights reserved. 

* This software is the confidential and proprietary 
* information of Shanghai eHealth Technology Company. 
* ("Confidential Information"). You shall not disclose 
* such Confidential Information and shall use it only 
* in accordance with the terms of the contract agreement 
* you entered into with Shanghai eHealth Technology Company. 
*/
package com.souyibao.shared.analysis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChineseSegmenter {
	Map<String, Double> dictionary = new HashMap<String, Double>();
	double minProbValue;
	
	public void segmentNormalizedString(StringBuffer strbuf, List<String> result) {
		char[] text = new char[strbuf.length()];
		strbuf.getChars(0, strbuf.length(), text, 0);
		segmentNormalizedString(text, 0, text.length, result);
	}

	public void segmentNormalizedString(String textstr, List<String> result) {
		char[] text = textstr.toCharArray();
		segmentNormalizedString(text, 0, text.length, result);
	}
	
	public void segmentNormalizedString(char[] text, int offset, int length, List<String> result) {
		// use dynamic programming
		int[] best = new int[length + 1];
		double[] score = new double[length + 1];
		best[length] = 0;
		
		for (int i = length - 1; i >= 0; --i) {
			double bestScore = Double.NEGATIVE_INFINITY;
			int bestJ = -1;
			for (int j = i + 1; j <= length; ++j) {
				String substring = new String(text, offset + i, j - i);
				if ( j > i + 1 && inDictionary(substring) ||
						j == i + 1) {
					double s = score[j] + dictionaryScore(substring);
					if (s >= bestScore) {
						bestScore = s;
						bestJ = j;
					}
				}
			}
			best[i] = bestJ;
			score[i]= bestScore;
		}
		int i = 0;
		while (i < length) {
			String s = new String(text, offset + i, best[i] - i);
			result.add(s);
			i = best[i];
		}
	}
	
	boolean inDictionary(String word) {
		return dictionary.containsKey(word);
	}
	
	double dictionaryScore(String word) {
		Double d = dictionary.get(word);
		if (d != null) {
			return d.doubleValue();
		} else {
			return minProbValue;
		}
	}	
	
	private void loadDictionary(InputStream stream) throws IOException {
		minProbValue = Double.MAX_VALUE;
		BufferedReader reader = new BufferedReader(
					new InputStreamReader(stream, "UTF8"));
		String line;
		while ( (line = reader.readLine()) != null) {
			int index = line.indexOf('\t');
			if (index != -1) {
				String word = line.substring(0, index);
				double log = Double.parseDouble(line.substring(index+1));
				dictionary.put(word.intern(), log);
				if (minProbValue > log) {
					minProbValue = log;
				}
			}
		}
	}
	
	// singleton
	private static ChineseSegmenter segmenter = null;
	private ChineseSegmenter() {
		try {
			String path = "zh-cn.txt";
			InputStream stream = getClass().getClassLoader().getResourceAsStream(path);
			loadDictionary(stream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static synchronized ChineseSegmenter getInstance() {
		if (segmenter == null) {
			segmenter = new ChineseSegmenter();
		}
		return segmenter;
	}
}
