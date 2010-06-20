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
