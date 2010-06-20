package com.souyibao.shared.util;

public class FingerPrintUtil {
	// TODO : need to implement the finger print algorithm
	public static int genStringId(String str) {
		return str.hashCode();
	}
}
