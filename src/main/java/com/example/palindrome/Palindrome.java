package com.example.palindrome;

public class Palindrome {
	static Boolean isPalindrome(String text) {

		text = text.toLowerCase();
		String forward = text;
		StringBuilder backward = new StringBuilder();
		
		backward.append(text);
		backward.reverse();

		if (forward.equals(backward.toString())) {
			return true;
		} else
			return false;
	}
}
