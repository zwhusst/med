package com.souyibao.pipe.keyword;

public class TestSplit {
	public static void main(String[] args) {
		String keyword = "感冒　伤风　急性　急";
		String[] words = keyword.split("[( )　/\\\\]+");
		
		for (int i = 0; i < words.length; i++) {
		System.out.println(words[i]);
		}				
	}
}
