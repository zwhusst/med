package com.souyibao.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.souyibao.shared.dao.IMedDAOConnection;
import com.souyibao.shared.dao.IMedDAOConnectionFactory;
import com.souyibao.shared.dao.JPAMedDaoConnectionFactory;
import com.souyibao.shared.entity.Area;
import com.souyibao.shared.entity.Hospital;

// this class is only used for design time
public class HospitalManager {
	private static HospitalManager instance = new HospitalManager();
	
	private IMedDAOConnectionFactory m_daoFactory = null;
	
	private String[] grades = { "三级甲等", "三级乙等", "三级丙等", "二级甲等", "二级乙等", "二级丙等",
			"一级甲等", "一级乙等", "一级丙等", "其他" };
	
	// area to a list of hospitals
	private Map<Area, List<Hospital>> m_areaToHospital = new HashMap<Area, List<Hospital>>();
	
	private HospitalManager(){
		m_daoFactory = new JPAMedDaoConnectionFactory();
	};
	
	public static HospitalManager getInstance() {
		return instance;
	}
	
	public List<Hospital> getHospitalWithArea(Area area) {
		List<Hospital> result = m_areaToHospital.get(area);
		
		if (result == null) {
			result = getTenHospitalWithArea(area);
			m_areaToHospital.put(area, result);
		}
		
		return result;
	}
	
	public List<Hospital> getTenHospitalWithArea(Area area) {
		IMedDAOConnection  con = m_daoFactory.openConnection();
		List<Hospital> hospitals = con.getHospitalWithArea(area);
		
		for (Hospital hospital : hospitals) {
			hospital.updateCategory(MedEntityManager.getInstance()
					.getIdToCategories());
		}
		
		List<Hospital> result = new ArrayList<Hospital>();
		for (Hospital hospital : hospitals ) {
			String grade = hospital.getGrade();
			
			if (grades[0].equals(grade)) {
				result.add(hospital);
			}
		}
				
//		for (Hospital hospital : hospitals ) {
//			String grade = hospital.getGrade();
//			
//			if (grades[1].equals(grade)) {
//				result.add(hospital);
//			}
//		}
		
		con.close();
		
		return result;
	}
}
