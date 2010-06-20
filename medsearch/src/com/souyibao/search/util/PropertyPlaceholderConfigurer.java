package com.souyibao.search.util;

import java.util.Properties;

public class PropertyPlaceholderConfigurer {
	private static String PLACEHOLDER_PREFIX = "${";

	private static String PLACEHOLDER_SUFFIX = "}";

	public static String parseValue(Properties prop, String strVal) {
		int startIndex = strVal.indexOf(PLACEHOLDER_PREFIX);
		int endIndex = strVal.indexOf(PLACEHOLDER_SUFFIX, startIndex
				+ PLACEHOLDER_PREFIX.length());
		if (startIndex != -1 && endIndex != -1) {
			String placeholder = strVal.substring(startIndex
					+ PLACEHOLDER_PREFIX.length(), endIndex);
			String propValue = prop.getProperty(placeholder);
			if (propValue != null) {
				return strVal.substring(0, startIndex) + propValue
						+ strVal.substring(endIndex + 1);
			}
		}
		return strVal;
	}


	public static void main(String[] args) {
		System.getProperties().setProperty("module_dir", "my tedsfsting_dir");

		System.out.println(PropertyPlaceholderConfigurer.parseValue(System.getProperties(),
				"${mod232ule_dir}/asfa"));
	}
}
