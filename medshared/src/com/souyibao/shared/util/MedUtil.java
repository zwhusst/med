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
package com.souyibao.shared.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.lucene.document.Field;

import com.souyibao.shared.entity.Keyword;

public class MedUtil {
	
	public static String getKeywordAliasText(Keyword keyword) {
		Collection<String> aliases = keyword.getAliasCollection();
		if ((aliases == null) || (aliases.isEmpty())) {
			return "";
		}

		StringBuffer data = new StringBuffer();
		for (Iterator<String> iterator = aliases.iterator(); iterator.hasNext();) {
			if (data.length() == 0) {
				data.append(iterator.next());
			} else {
				data.append("," + iterator.next());
			}
		}

		return data.toString();
	}
	
	public static short calMatchCount(String searchStr, String patternStr) {
		try {
			Pattern pattern = Pattern.compile(patternStr);

			short count = 0;
			Matcher matcher = pattern.matcher(searchStr);

			while (matcher.find()) {
				count++;
			}
			return count;
		} catch (Exception e) {
			// System.out.println("pattern is: " + patternStr);
			return 0;
		}
	}

	public static int calMatchCount(org.apache.lucene.document.Document doc,
			String patternStr) {
		List fields = doc.getFields();
		String docId = doc.get("id");
		StringBuffer strBuf = new StringBuffer();

		for (Iterator iterator = fields.iterator(); iterator.hasNext();) {
			Field field = (Field) iterator.next();

			if (!"id".equals(field.name())) {
				String value = doc.get(field.name());
				strBuf.append(value);
			}
		}

		return calMatchCount(strBuf.toString(), patternStr);
	}

	public static int calKeywordMatchcount(
			org.apache.lucene.document.Document doc, Keyword keyword) {
		int count = 0;
		String keywordName = keyword.getName();

		count = calMatchCount(doc, keywordName);
		// check the keyword alias
		Collection<String> alias = keyword.getAliasCollection();
		if ((alias != null) && (!alias.isEmpty())) {
			for (Iterator<String> aliasIte = alias.iterator(); aliasIte
					.hasNext();) {
				// parts.addAll(getKeywordParts(aliasIte.next()));
				count += calMatchCount(doc, aliasIte.next());
			}
		}

		return count;
	}

	public static String[] splitKeyword(String keyword) {
		String[] splitKeys = keyword.split("[( )、,（），]+");

		for (int i = 0; i < splitKeys.length; i++) {
			if (splitKeys[i] != null) {
				splitKeys[i] = splitKeys[i].replaceAll("&nbsp;", " ");
				splitKeys[i] = splitKeys[i].replaceAll("&#40;", "(");
				splitKeys[i] = splitKeys[i].replaceAll("&#41;", ")");
				splitKeys[i] = splitKeys[i].replaceAll("&#44;", ",");
			}
		}

		return splitKeys;
	}

	public static String normalizeCJK(String str) {
		if (str == null) {
			return "";
		}

		String temp = str.replaceAll("《", "<");
		temp = temp.replaceAll("》", ">");
		temp = temp.replaceAll("。", ".");
		temp = temp.replaceAll("、", ",");

		return toDbcCaseString(temp);
	}

	public static String toDbcCaseString(String str) {
		if (str == null) {
			return "";
		}

		StringBuffer temp = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			temp.append(toDbcCase(str.charAt(i)));
		}

		return temp.toString();
	}

	public static String joinString(Collection<String> values, String sep) {
		if ((values == null) || (values.isEmpty())) {
			return null;
		}
		
		boolean isFirst = true;
		StringBuffer result = new StringBuffer();
		for (String val : values) {
			if (isFirst) {
				isFirst = false;
			} else {
				result.append(sep);
			}

			result.append(val);
		}
		
		return result.toString();
	}

	
	/**
	 * QJ to BJ
	 * 
	 * @param src
	 * @return
	 */
	public static char toDbcCase(char src) {
		if (src == 12288) {
			src = (char) 32;
		} else if (src > 65280 && src < 65375) {
			src = (char) (src - 65248);
		}
		return src;
	}

	public static void main(String[] data) {
		String test = "、ＡＱＳＰＯＱＷ～｀！·＃￥％……—＊（）——＋－＝｛｝［］：“；‘《》？，。／";

		System.out.println(toDbcCaseString(test));
		System.out.println(normalizeCJK(test));
	}
}
