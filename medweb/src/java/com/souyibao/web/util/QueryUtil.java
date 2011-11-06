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
package com.souyibao.web.util;

import java.util.List;

import com.souyibao.freemarker.DoctorViewer;
import com.souyibao.shared.MedEntityManager;

public class QueryUtil {
	public static List<DoctorViewer> queryDoctorByCategoryID(String id) {
		String className = DoctorViewer.class.getCanonicalName();
		String jql = "select new "
				+ className
				+ "(m.id, m.name, m.area.name, m.grade, m.traits, m.hospitalName, m.department, m.profile, m.url) from Doctor m, "
				+ "in (m.topDoctorCategorys) t where t.id=?1";

		Object[] paras = new Object[] { new Long(id) };
		List<DoctorViewer> data = (List<DoctorViewer>) MedEntityManager
				.getInstance().query(jql, paras, -1, -1);

		return data;
	}
	
	public static List<DoctorViewer> queryTDocByCategoryID(String id) {
		String className = DoctorViewer.class.getCanonicalName();
		String jql = "select new "
				+ className
				+ "(m.doctor.id, m.doctor.name, m.doctor.area.name, m.doctor.grade, m.doctor.traits, m.doctor.hospitalName, m.doctor.department, m.doctor.profile, m.doctor.url) from TopDoctor m "
				+ "where m.category.id=?1";

		Object[] paras = new Object[] { new Long(id) };
		List<DoctorViewer> data = (List<DoctorViewer>) MedEntityManager
				.getInstance().query(jql, paras, -1, -1);

		return data;
		
	}
	
	public static void main(String[] args){
		List<DoctorViewer> docs =  queryTDocByCategoryID("28");
		System.out.println(docs.size());
	}
}
