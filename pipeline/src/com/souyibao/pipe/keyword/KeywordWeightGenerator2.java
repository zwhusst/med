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

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.souyibao.shared.analysis.MedAnalyzer;
import com.souyibao.shared.dao.IMedDAOConnection;
import com.souyibao.shared.dao.JPAMedDaoConnectionFactory;
import com.souyibao.shared.util.MedUtil;

public class KeywordWeightGenerator2 {

	private static Collection<TFModel> TFDatas = new ArrayList<TFModel>();
	private static HashMap<String, Integer> KeywordDW = new HashMap<String, Integer>();
	private static HashMap<String, KeywordPartTF> KeywordPartsTF = new HashMap<String, KeywordPartTF>();
	private static HashMap<String, Set<String>> KeywordTokens = new HashMap<String, Set<String>>();
	private static MedAnalyzer ANALYZER = new MedAnalyzer();

	// just one flag
	private static float NO_TOKEN_IN_DOC = - 1.0f;
	public static void main(String[] args) throws Exception {
		String usage = "java com.souyibao.pipe.keyword.KeywordWeightGenerator <idx_folder> <output_folder>";
		if (args.length != 2) {
			System.err.println("Usage: " + usage);
			System.exit(1);
		}
		
		JPAMedDaoConnectionFactory factory = new JPAMedDaoConnectionFactory();
		IMedDAOConnection con = factory.openConnection();

		genKeywordWeight(con, args[0], args[1]);
		
		con.close();
	}

	private static void genKeywordWeight(IMedDAOConnection con, String idxFolder, String outputFolder) throws Exception {
		String sql = "select id, name, pname, alias from keyword where  topicid != 5 and topicid != 6";
		List keywords = con.executeSQL(sql);

		try {
			Set<String> keywordPartsSet = getKeyportParts(keywords);
			Directory indexDir = FSDirectory.open(new File(idxFolder));
			IndexReader idxReader = IndexReader.open(indexDir);

			int numDocs = idxReader.numDocs();
			
			long startTime = System.currentTimeMillis();
			System.out.println("start genKeywordWeight free memory: " + Runtime.getRuntime().freeMemory() / 1024 + "K");
			for (int i = 0; i < numDocs; i++) {
				org.apache.lucene.document.Document doc = idxReader.document(i);
				createTFAndDw(doc, keywordPartsSet);
			}
			
			idxReader.close();
			
			System.out.println("end genKeywordWeight free memory: " + Runtime.getRuntime().freeMemory() / 1024 + "K");
			long endTime = System.currentTimeMillis();
			System.out.println("take time to createTF and DW(min): " + (endTime - startTime)/60000);

			// build the output data file
			String docToKeywordPath = outputFolder + File.separator
					+ "doc2keyword";
			File docToKeywordFile = new File(docToKeywordPath);
			if (docToKeywordFile.exists()) {
				docToKeywordFile.delete();
			}

			docToKeywordFile.createNewFile();
			DataOutputStream outStream = new DataOutputStream(
					new FileOutputStream(docToKeywordPath));
	
//			// calculate the weight
			System.out.println("total document: " + numDocs);
			Collection<TFModel> tfModels = calculateWeightAndOutput(numDocs, outStream);
			
//			outputWeightData(outStream, tfModels);
			outStream.close();

			System.out.println("doc to keyword has been generated: "
					+ docToKeywordPath);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// select id, name, pname, alias from keyword
	private static Set<String> getKeyportParts(List keywords) throws Exception{
		System.out.println("start getKeyportPartTF free memory: " + Runtime.getRuntime().freeMemory() / 1024 + "K");
		
		Set<String> keywordPartsSet = new HashSet<String>();
		for (Object data : keywords) {
			Object[] dataArray = (Object[])data;
			
			Integer id = (Integer)dataArray[0];
			String name = (String)dataArray[1];
			String pName = (String)dataArray[2];
			String aliasStr = (String)dataArray[3];
			
			Set<String> parts = new HashSet();
			parts.add(((pName == null) || ("".equals(pName)))? name: pName);
			Collection<String> alias = parseAlias(aliasStr);
//			Collection<String> alias = keyword.getAliases();
			if ((alias != null) && (!alias.isEmpty())) {
				for (Iterator<String> aliasIte = alias.iterator(); aliasIte
						.hasNext();) {
//					parts.addAll(getKeywordParts(aliasIte.next()));
					parts.add(aliasIte.next());
				}
			}
			
			keywordPartsSet.addAll(parts);
			KeywordTokens.put(""+id, parts);
		}
		
		System.out.println("total keyword parts: " + keywordPartsSet.size());
		System.out.println("end getKeyportPartTF free memory: " + Runtime.getRuntime().freeMemory() / 1024 + "K");
		
		return keywordPartsSet;
	}

	private static Collection<String> parseAlias(String data) {
		if ((data == null) || ("".equals(data.trim()))) {
			return null;
		}
		
		Collection<String> alias = new ArrayList<String>();
		// check the class PipelineUtil, method: createKeywordData
		String[] temp = data.split("\\|");
		for (int i = 0; i < temp.length; i++) {
			alias.add(temp[i]);
		}
		
		return alias;
	}
	
	
	private static void createTFAndDw(org.apache.lucene.document.Document doc,
			Set<String> keywordPartsSet) {

		List fields = doc.getFields();
		String docId = doc.get("id");
		StringBuffer strBuf = new StringBuffer();

		for (Iterator iterator = fields.iterator(); iterator.hasNext();) {
			Field field = (Field) iterator.next();

			if (!"id".equals(field.name()) && (!"notice".equals(field.name()))) {
				String value = doc.get(field.name());
				strBuf.append(value);
			}
		}
		String searchStr = strBuf.toString();

		KeywordPartTF keywordPartTf = null;
		for (Iterator<String> iterator = keywordPartsSet.iterator(); iterator
				.hasNext();) {
			String keywordPart = iterator.next();

			if ("".equals(keywordPart)) {
				continue;
			}
			
			short count = MedUtil.calMatchCount(searchStr, keywordPart);
			if (count != 0) {
				if (keywordPartTf == null) {
					keywordPartTf = new KeywordPartTF();
					keywordPartTf.setDocId(docId);
					KeywordPartsTF.put(docId, keywordPartTf);
				}
				keywordPartTf.addPartCount(keywordPart, count);
				
				
				// prepare data for idf Dw
				if (KeywordDW.get(keywordPart) == null) {
					KeywordDW.put(keywordPart, 1);
				} else {
					int kDW = KeywordDW.get(keywordPart);
					kDW++;
					KeywordDW.put(keywordPart, kDW);
				}
			}
		}
	}
	
	private static Collection<TFModel> calculateWeightAndOutput(int totalDocs,
			DataOutputStream outputStream) {	
		Set docIdSet = KeywordPartsTF.keySet();
		
		int i = 0;
		Collection<TFModel> tfModels = new ArrayList<TFModel>();		
		for (Iterator<String> iterator = docIdSet.iterator(); iterator.hasNext();) {
			String docId = iterator.next();
			KeywordPartTF keywordPartTF = KeywordPartsTF.get(docId);
						
			Set keywordIdSet = KeywordTokens.keySet();
			for (Iterator<String> keywordIte = keywordIdSet.iterator(); keywordIte.hasNext(); ) {
				String keywordId = keywordIte.next();
				
				Set tokens = KeywordTokens.get(keywordId);
				
				float weight = calculateWeight4KeywordTokens(tokens, KeywordDW,
						keywordPartTF, totalDocs);
				
				if (weight >= 0.0f) {
					TFModel tfModel = new TFModel();
					tfModel.setDocId(docId);
					tfModel.setKeywordId(keywordId);
					tfModel.setWeight(weight);
//					tfModel.setIsNotice(keywordPartTF.getIsNotice());
					
					tfModels.add(tfModel);
				}
				
//				System.out.println("doc: "
//						+ docId
//						+ " keywordId: "
//						+ KeywordManager.getInstance().getKeywordById(
//								Integer.parseInt(keywordId)).getName()
//						+ " weight: " + weight);
			}
			
			outputWeightData(outputStream, tfModels);
			tfModels.clear();
			
			i++;
			if ((i % 100) == 0) {
				System.out.println("Processed: " + (100* i/totalDocs) + "%");
			}
		}
			
		return tfModels;
	}
	
	private static float calculateWeight4KeywordTokens(Set<String> tokens,
			HashMap<String, Integer> keywordDW, KeywordPartTF keywordtf, int totalDocs) {
		int totalCount = keywordtf.getTotalCount();
		
		boolean empty_token_in_doc = true;
		float weight = 0.0f;
		for (Iterator<String> iterator = tokens.iterator(); iterator.hasNext();) {
			String token = iterator.next();

			int count = keywordtf.getCount(token);
			if (count == 0) {
				continue;
			}
			
			empty_token_in_doc = false;
			int kDW = keywordDW.get(token);
			// tf * idf
			weight = weight + ((float)count / totalCount)
					* (float) java.lang.Math.log((double)totalDocs / kDW);
		}

		if (empty_token_in_doc) {
			return NO_TOKEN_IN_DOC;
		}
		return weight;
	}
	
	private static void outputWeightData(DataOutputStream outStream,
			Collection<TFModel> weightData) {
		for (Iterator<TFModel> iterator = weightData.iterator(); iterator
				.hasNext();) {
			TFModel tfModel = iterator.next();

			StringBuffer outString = new StringBuffer();
			outString.append(tfModel.getDocId());
			outString.append("\0");
//			outString.append(tfModel.getIsNotice());
//			outString.append("\0");
			outString.append(tfModel.getKeywordId());
			outString.append("\0");
			outString.append(tfModel.getWeight());
			outString.append("\r\n");

			try {
				outStream.writeUTF(outString.toString());
				outStream.flush();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
}
