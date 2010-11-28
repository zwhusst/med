package com.souyibao.shared.util;

import java.io.File;
import java.util.List;

import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;

import com.souyibao.shared.analysis.MedAnalyzer;
import com.souyibao.shared.dao.IMedDAOConnection;
import com.souyibao.shared.dao.IMedDAOConnectionFactory;
import com.souyibao.shared.dao.JPAMedDaoConnectionFactory;
import com.souyibao.shared.entity.Area;
import com.souyibao.shared.entity.Doctor;
import com.souyibao.shared.entity.Document;

public class PipelineUtil {

	public static void indexDoctor(File folder) throws Exception {
		IMedDAOConnectionFactory factory = new JPAMedDaoConnectionFactory();
		IMedDAOConnection con = factory.openConnection();

		List<Area> areas = con.loadAllAreas();
		
		for (Area area : areas) {
			if (area.getId() == 0) {
				continue;
			}
			
			List<Doctor> doctors = con.loadDoctorsByArea(area.getId());
			String idxPath = folder.getAbsolutePath() + File.separator + area.getId();
			IndexWriter writer = new IndexWriter(idxPath, new MedAnalyzer(), true);
			
			for (Doctor doc : doctors) {
				long id = doc.getId();
				String profile = doc.getProfile();
				String other = doc.getOther();

				org.apache.lucene.document.Document lucDoc = new org.apache.lucene.document.Document();

				// content
				lucDoc.add(new Field("content", profile + other,
						Field.Store.YES, Field.Index.ANALYZED));

				// id
				lucDoc.add(new Field("id", "" + id, Field.Store.YES,
						Field.Index.ANALYZED_NO_NORMS));

				writer.addDocument(lucDoc);
			}
			
			writer.optimize();
			writer.close();
		}
		
		con.close();
	}
	
	public static void indexDocs(IndexWriter writer) throws Exception {

		IMedDAOConnectionFactory factory = new JPAMedDaoConnectionFactory();
		IMedDAOConnection con = factory.openConnection();

		// documents, it is used for the medical type keyword
		List<Document> docs = con.loadAllDocuments();

		for (Document doc : docs) {
			long docId = doc.getId();
			String name = doc.getName();
			String content = doc.getContent();

			org.apache.lucene.document.Document lucDoc = new org.apache.lucene.document.Document();

			// content
			lucDoc.add(new Field("content", name + content, Field.Store.YES,
					Field.Index.ANALYZED));

			// id
			lucDoc.add(new Field("id", "" + docId, Field.Store.YES,
					Field.Index.ANALYZED_NO_NORMS));

			writer.addDocument(lucDoc);
		}

		con.close();
		writer.optimize();
		writer.close();

	}
	
	
	public static void indexKeywords(IndexWriter writer) throws Exception {
		IMedDAOConnectionFactory factory = new JPAMedDaoConnectionFactory();
		IMedDAOConnection con = factory.openConnection();

		String sql = "select id, name, pname, explanation, indexcontent from keyword";
		// documents, it is used for the medical type keyword
		List docs = con.executeSQL(sql);

		for (Object data : docs) {
			Object[] dataArray = (Object[])data;
			Integer id = (Integer)dataArray[0];
			String name = (String)dataArray[1];
			String pname = (String)dataArray[2];
			String explanation = (String)dataArray[3];
			String indexcontent = (String)dataArray[4];

			org.apache.lucene.document.Document lucDoc = new org.apache.lucene.document.Document();

			if ((pname != null) && (!pname.isEmpty())) {
				name = pname;
			}
			
			// content
			if ((indexcontent != null) && (indexcontent.length() > 0)) {
				lucDoc.add(new Field("content", indexcontent,
						Field.Store.YES, Field.Index.ANALYZED));				
			} else {
				lucDoc.add(new Field("content", name + explanation,
						Field.Store.YES, Field.Index.ANALYZED));
			}

			// id
			lucDoc.add(new Field("id", "" + id, Field.Store.YES,
					Field.Index.ANALYZED_NO_NORMS));

			writer.addDocument(lucDoc);
		}
//
		con.close();
		writer.optimize();
		writer.close();

	}

}
