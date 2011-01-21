package com.souyibao.restlet;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.CharacterSet;
import org.restlet.data.MediaType;
import org.restlet.representation.StringRepresentation;

import com.souyibao.freemarker.DoctorViewer;
import com.souyibao.freemarker.GuideInfoDataModel;
import com.souyibao.freemarker.HospitalViewer;
import com.souyibao.shared.MedEntityManager;
import com.souyibao.shared.entity.Area;
import com.souyibao.shared.entity.Keyword;
import com.souyibao.web.util.MedWebUtil;
import com.souyibao.web.util.QueryUtil;

public class GuideInfoRestlet extends BaseRestlet {
	
	public static String HOSPITAL_GUIDE_TYPE = "hospital";
	public static String DOCTOR_GUIDE_TYPE = "doctor";
	
	@Override
	public void handle(Request request, Response response) {
		String area = (String) request.getAttributes().get("area");
		String guidetype = (String) request.getAttributes().get("guidetype");
		String querykeywords = (String) request.getAttributes().get("querykeywords");
		String curentkeyword = (String) request.getAttributes().get(
				"curentkeyword");
		String currentcategory = (String) request.getAttributes().get(
				"currentcategory");
		
		// query keywords
		String[] queryKeywordIds = null;
		if (querykeywords != null) {
			queryKeywordIds = querykeywords.split(",");
		}
		
		// data model
		GuideInfoDataModel dataModel = processInput(area, guidetype,
				queryKeywordIds, curentkeyword, currentcategory);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("guideinfo", dataModel);
		
		String outValue = this.processData(request, data);
		
		// output
		StringRepresentation output = new StringRepresentation(outValue,
				MediaType.TEXT_HTML, null, new CharacterSet("UTF-8"));
		response.setEntity(output);	
	}
	
	private GuideInfoDataModel processInput(String areaId, String guideType,
			String[] queryKeywordIds, String keywordId, String categoryId) {
		//  query for the doctor
		List<DoctorViewer> docs = QueryUtil.queryTDocByCategoryID(categoryId);
		
		// query for hospital
		areaId = (areaId == null)? "0" : areaId;
		List<HospitalViewer> hospitals = MedWebUtil.getFilterHospital(categoryId, areaId);

		// query for all the keyword
		Set<Keyword> keywords = MedEntityManager.getInstance().getKeysWithIds(
				queryKeywordIds);
		
		// all areas
		Collection<Area> areas = MedEntityManager.getInstance().getAllAreas();
		
		GuideInfoDataModel dataModel = new GuideInfoDataModel();
		dataModel.setAreaId(areaId);
		dataModel.setDoctors(docs);
		dataModel.setGuideType(guideType);
		dataModel.setHospitals(hospitals);
		dataModel.setQueryCategoryId(categoryId);
		dataModel.setQueryKeywordId(keywordId);
		dataModel.setDiagnoseKeywords(keywords);
		dataModel.setAreas(areas);
		
		return dataModel;
	}
}
