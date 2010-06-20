package com.souyibao.web.util;

public class WebConstants {
	// definition for search type, explanation type, admin type
	public static final String UNDEFINED_TYPE = "0";
	public static final String KEYWORD_ID_TYPE = "1";
	public static final String DOCUMENT_ID_TYPE = "2";
	public static final String KEYWORD_TOPIC_TYPE = "3";
	public static final String DOC_TOPIC_TYPE = "4";
	
	// operation type
	public static final String ADD_TYPE = "0";
	public static final String DELETE_TYPE = "1";
	public static final String UPDATE_TYPE = "2";
	
	// for the request parameter
	public static final String PARA_QUERY_STRING = "q";
	public static final String PARA_CATA_FILTER = "cFilter";
	public static final String PARA_TOPIC_FILTER = "tFilter";
	public static final String PARA_KEYWORD = "k";
	public static final String PARA_HANDLER = "hl";
	public static final String PARA_FORWARD_NAME = "fw";
	public static final String PARA_DEPRECATE_USER_INPUT = "dinput";
	
	// data attribute name of request
	public static final String DATA_TOPIC_RESULT = "topicResult";
	public static final String DATA_HIDDEN_MES = "hiddenmes";
	public static final String DATA_ALL_QUERY_URL = "allQueryUrl";
	public static final String DATA_KEYWORD_URL = "keywordUrl";
	public static final String DATA_EXPLANATION = "explanation";
	public static final String DATA_TOPIC_NAV_INFO = "topicNavInfo";
}
