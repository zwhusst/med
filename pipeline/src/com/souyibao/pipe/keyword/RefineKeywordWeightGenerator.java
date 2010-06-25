package com.souyibao.pipe.keyword;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

public class RefineKeywordWeightGenerator {
	
	private static final String KEYWORD_WEIGHT_FILE = 
		"E:\\Medsearch\\cvsroot\\projects\\medshared\\bak\\doc2keyword2";
	private static final float THRES_HOLD_VAL = 0.15f;
	
	public static void main(String[] args) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(new File(
				KEYWORD_WEIGHT_FILE)));
		
		String docToKeywordPath = "E:\\Medsearch\\cvsroot\\projects\\medshared\\conf\\doc2keyword";
		File docToKeywordFile = new File(docToKeywordPath);
		if (docToKeywordFile.exists()) {
			docToKeywordFile.delete();
		}
		
		docToKeywordFile.createNewFile();
		DataOutputStream outStream = new DataOutputStream(
				new FileOutputStream(docToKeywordPath));
		
		String data = null;
//		StringBuffer outString = new StringBuffer();
		while((data = reader.readLine()) != null) {
			float weight = getWeight(data);
			if (getWeight(data) >= THRES_HOLD_VAL) {
				int idx = data.indexOf(32430);
				String docId = data.substring(0, idx).trim();
				int idx2 = data.indexOf(32430, idx + 1);
				String keywordId = data.substring(idx+ 1, idx2).trim();
				
				StringBuffer outString = new StringBuffer();
				outString.append(docId);
				outString.append("\0");
				outString.append(keywordId);
				outString.append("\0");
				outString.append(weight);
				outString.append("\r\n");
				outStream.writeUTF(outString.toString());
			}
		}
//		outStream.writeUTF(outString.toString());
		outStream.flush();
		outStream.close();
	}
	
	private static float getWeight(String data) throws Exception {

		int idx = data.lastIndexOf(32430);
		float weight = Float.parseFloat(data.substring(idx + 1));

		return weight;
	}
}
