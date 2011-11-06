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
package com.souyibao.pipe.idx;

import java.io.File;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.souyibao.shared.analysis.MedAnalyzer;
import com.souyibao.shared.util.PipelineUtil;

public class IdxGenerator {

	public static void main(String[] args) throws Exception {
		String usage = "java com.souyibao.pipe.idx.IdxGenerator <index_directory>";
		if (args.length < 1) {
			System.err.println("Usage: " + usage);
			System.exit(1);
		}

		String filePath = args[args.length - 1];
		try {
			Directory indexDir = FSDirectory.open(new File(
					filePath));
			IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_31, new MedAnalyzer());
			config.setOpenMode(OpenMode.CREATE);
			
			IndexWriter writer = new IndexWriter(indexDir, config);
			PipelineUtil.indexKeywords(writer);
		} catch (Exception e) {
			System.out.println(" caught a " + e.getClass()
					+ "\n with message: " + e.getMessage());
			throw e;
		}
	}

//	private static void indexDocs(IndexWriter writer, File sourceFile)
//			throws Exception {
//		System.out.println("adding " + sourceFile);
//		DocumentBuilder builder = DocumentBuilderFactory.newInstance()
//				.newDocumentBuilder();
//
//		org.w3c.dom.Document document = builder.parse(sourceFile);
//		Element rootEle = document.getDocumentElement();
//		String topicId = rootEle.getAttribute("id");
//		Topic topic = TopicsManager.getInstance().getTopicById(topicId);
//
////		String category = rootEle.getAttribute("category");
//		Collection<TopicItem> items = topic.getItems();
//		NodeList eles = document.getElementsByTagName("data");
//		
//		// only to keep three fields to lucence index: id, name, content
//		for (int i = 0, size = eles.getLength(); i < size; i++) {
//			Document doc = new Document();
//			Node node = eles.item(i);
//
//			StringBuffer contentData = new StringBuffer();
//			for (Iterator<TopicItem> iterator = items.iterator(); iterator
//					.hasNext();) {
//				TopicItem item = iterator.next();
//
//				String data = DOMUtil.getTextElementValue(node, item.getName());
//				if (item.getName().equals("name")) {
//					// name
//					doc.add(new Field("name", data, Field.Store.YES,
//							Field.Index.TOKENIZED));
//				} else if (item.getName().equals("filepath")) {
//					// check the code located in class <FeedManager.createElement()>
//				}else {
//					contentData.append(data);
//				}
//			}
//			
//			// content
//			doc.add(new Field("content", contentData.toString(), Field.Store.YES,
//					Field.Index.TOKENIZED));
//			
//			// id
//			doc.add(new Field("id", "" + docId++, Field.Store.YES,
//					Field.Index.TOKENIZED));
//			
////			// category 
////			if ((category != null) && (!"".equals(category))) {
////				doc.add(new Field("category", category, Field.Store.YES,
////						Field.Index.TOKENIZED));
////			}
//			writer.addDocument(doc);
//			
//		}
//	}
}
