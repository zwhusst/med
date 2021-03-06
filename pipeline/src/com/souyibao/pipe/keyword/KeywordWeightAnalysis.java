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
package com.souyibao.pipe.keyword;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

public class KeywordWeightAnalysis {
	private static final String KEYWORD_WEIGHT_FILE = "E:\\Medsearch\\cvsroot\\projects\\medshared\\conf\\doc2keyword";

	public static void main(String[] args) throws Exception {
//		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//
//		while (true) {
//			System.out.print("Text: ");
//			String line = in.readLine();
//			
//			if ((line == null) || line.length() == 0) {
//				break;
//			}
//			
//			BufferedReader reader = new BufferedReader(new FileReader(
//					new File(KEYWORD_WEIGHT_FILE)));
//
//			calculatePercentt(reader, Float.parseFloat(line));
//			reader.close();
//		}

		DataInputStream dataStream = new DataInputStream(new FileInputStream(new File(KEYWORD_WEIGHT_FILE)));
		String data = dataStream.readUTF();
		dataStream.close();
	}
	
	private static void calculatePercentt(BufferedReader reader, float valueHold) throws Exception {
		String data = null;
		
		int totalCount = 0;
		int number = 0;
		while ((data = reader.readLine()) != null) {
			int idx = data.lastIndexOf(32430);
			float weight = Float.parseFloat(data.substring(idx + 1));
			
			if (weight >= valueHold) {
				number++;
			}
			
			totalCount++;
		}
		
		System.out.println("number is: " + number + " percent: " + 100 * (float)number
				/ totalCount + "%");
	}
}
