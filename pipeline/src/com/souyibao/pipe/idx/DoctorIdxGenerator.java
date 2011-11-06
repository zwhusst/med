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

import com.souyibao.shared.util.PipelineUtil;

public class DoctorIdxGenerator {

	public static void main(String[] args) throws Exception {
		String usage = "java com.souyibao.pipe.idx.IdxGenerator <index_directory>";
		if (args.length < 1) {
			System.err.println("Usage: " + usage);
			System.exit(1);
		}

		File file = new File(args[args.length - 1]);
		if (!file.exists()) {
			file.mkdir();
		}
		if (!file.isDirectory()) {
			file.mkdir();
		}
		
		try {			
			PipelineUtil.indexDoctor(file);
		} catch (Exception e) {
			System.out.println(" caught a " + e.getClass()
					+ "\n with message: " + e.getMessage());
		}
	}
}
