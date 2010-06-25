package com.souyibao.pipe.idx;

import org.apache.lucene.index.IndexWriter;

import com.souyibao.shared.analysis.MedAnalyzer;
import com.souyibao.shared.util.PipelineUtil;

public class IdxGenerator {

	public static void main(String[] args) throws Exception {
		String usage = "java com.souyibao.pipe.idx.IdxGenerator <index_directory>";
		if (args.length < 1) {
			System.err.println("Usage: " + usage);
			System.exit(1);
		}

		try {
			IndexWriter writer = new IndexWriter(args[args.length - 1],
					new MedAnalyzer(), true);
			
//			PipelineUtil.indexDocs(writer);
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
